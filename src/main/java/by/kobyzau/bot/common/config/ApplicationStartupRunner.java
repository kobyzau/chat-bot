package by.kobyzau.bot.common.config;

import by.kobyzau.bot.common.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

  @Autowired private DataSource dataSource;
  @Autowired private Environment env;

  @Autowired private List<Bot> bots;


  @Override
  public void run(ApplicationArguments args) {
    try (Connection c = dataSource.getConnection();
         Statement s = c.createStatement()) {
      s.execute(
          "CREATE TABLE IF NOT EXISTS message(\n"
              + "    id serial PRIMARY KEY,\n"
              + "    message_id int NOT NULL,\n"
              + "    chat_id bigint NOT NULL,\n"
              + "    time TIMESTAMP NOT NULL\n"
              + ");");
    } catch (Exception e) {
      throw new RuntimeException("Cannot execute runtime sql", e);
    }
    bots.forEach(Bot::connect);
  }
}
