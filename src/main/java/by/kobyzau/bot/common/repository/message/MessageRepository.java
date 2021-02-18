package by.kobyzau.bot.common.repository.message;

import by.kobyzau.bot.common.entity.Message;

import java.util.List;

public interface MessageRepository {

    long saveMessage(Message message);

    void delete(long id);

    List<Message> getMessages();
}
