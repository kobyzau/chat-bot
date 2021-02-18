package by.kobyzau.bot.common.repository.message;

import by.kobyzau.bot.common.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Profile("prod")
public class DBMessageRepository implements MessageRepository {

    @Autowired
    private IMessageRepo repo;

    @Override
    public long saveMessage(Message message) {
        return repo.save(message).getId();
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Message> getMessages() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
