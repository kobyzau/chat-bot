package by.kobyzau.bot.common.repository.message;

import by.kobyzau.bot.common.entity.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class HashMapMessageRepository implements MessageRepository {

  private final Map<Long, Message> map = new HashMap<>();

  @Override
  public long saveMessage(Message message) {
    long newId = message.getId() > 0 ? message.getId() : getNewId();
    map.put(
        newId, new Message(newId, message.getChatId(), message.getMessageId(), message.getTime()));
    return newId;
  }

  @Override
  public List<Message> getMessages() {
    return map.values().stream()
        .map(m -> new Message(m.getId(), m.getChatId(), m.getMessageId(), m.getTime()))
        .collect(Collectors.toList());
  }

  @Override
  public void delete(long id) {
    map.remove(id);
  }

  private synchronized long getNewId() {
    long newId = map.keySet().stream().max(Long::compareTo).orElse(1L) + 1;
    map.put(newId, null);
    return newId;
  }
}
