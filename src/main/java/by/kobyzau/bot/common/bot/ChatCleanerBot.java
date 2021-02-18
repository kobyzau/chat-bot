package by.kobyzau.bot.common.bot;

import by.kobyzau.bot.common.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.Executor;

@Component("chatCleanerBot")
public class ChatCleanerBot extends TelegramLongPollingBot implements Bot {

  @Value("${bot.reconnect}")
  private int reconnectTime;

  @Value("${bot.username}")
  private String botUserName;

  @Value("${bot.token}")
  private String botToken;

  @Autowired private MessageService messageService;

  @Autowired
  @Qualifier("botUpdateExecutor")
  private Executor executor;

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public String getBotUsername() {
    return botUserName;
  }

  @Override
  public <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> send(Method method) {

    try {
      return Optional.ofNullable(super.execute(method));
    } catch (TelegramApiException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public void connect() {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(this);
    } catch (TelegramApiException e) {
      System.err.println("Cant Connect. Pause " + reconnectTime / 1000 + "sec and try again");
      e.printStackTrace();
      try {
        Thread.sleep(reconnectTime);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
        return;
      }
      connect();
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    executor.execute(() -> saveMessage(update));
  }

  private void saveMessage(Update update) {
    if (update.hasMessage()) {
      System.out.println("Saving message...");
      Message message = update.getMessage();
      messageService.saveMessage(message.getChatId(), message.getMessageId());
    }
  }
}
