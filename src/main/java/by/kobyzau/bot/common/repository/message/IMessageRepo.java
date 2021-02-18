package by.kobyzau.bot.common.repository.message;

import by.kobyzau.bot.common.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface IMessageRepo extends CrudRepository<Message, Long> {
}
