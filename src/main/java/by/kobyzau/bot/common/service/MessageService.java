package by.kobyzau.bot.common.service;

import by.kobyzau.bot.common.entity.Message;

import java.util.List;

public interface MessageService {

    void saveMessage(long chatId, int messageId);

    void deleteMessage(long id);

    List<Message> getMessages();
}
