package by.kobyzau.bot.common.service.impl;

import by.kobyzau.bot.common.entity.Message;
import by.kobyzau.bot.common.util.DateUtil;
import by.kobyzau.bot.common.repository.message.MessageRepository;
import by.kobyzau.bot.common.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired private MessageRepository messageRepository;

  @Override
  public List<Message> getMessages() {
    return messageRepository.getMessages();
  }

  @Override
  public void deleteMessage(long id) {
    messageRepository.delete(id);
  }

  @Override
  public void saveMessage(long chatId, int messageId) {
    messageRepository.saveMessage(new Message(chatId, messageId, DateUtil.currentTime()));
  }
}
