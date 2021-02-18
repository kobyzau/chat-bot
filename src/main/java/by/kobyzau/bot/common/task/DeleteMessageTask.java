package by.kobyzau.bot.common.task;

import by.kobyzau.bot.common.bot.Bot;
import by.kobyzau.bot.common.entity.Message;
import by.kobyzau.bot.common.service.MessageService;
import by.kobyzau.bot.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class DeleteMessageTask {

  @Value("${task.deleteMessage.hoursToLive}")
  private int hoursToLive;

  @Autowired private MessageService messageService;

  @Autowired private Bot chatCleanerBot;

  @Autowired private Executor taskExecutor;

  @Scheduled(fixedDelayString = "${task.deleteMessage.delay}", zone = "GMT+3.00")
  public void deleteMessage() {
    System.out.println("Checking messages...");
    LocalDateTime fromTime = DateUtil.currentTime().minusHours(hoursToLive);
    messageService.getMessages().stream()
        .filter(m -> m.getTime().isBefore(fromTime))
        .collect(Collectors.toList())
        .forEach(m -> taskExecutor.execute(() -> handleMessage(m)));
  }

  private void handleMessage(Message message) {
    boolean success = chatCleanerBot.send(toRequest(message)).orElse(false);
    if (success) {
      messageService.deleteMessage(message.getId());
    }
  }

  private DeleteMessage toRequest(Message message) {
    return DeleteMessage.builder()
        .chatId(String.valueOf(message.getChatId()))
        .messageId(message.getMessageId())
        .build();
  }
}
