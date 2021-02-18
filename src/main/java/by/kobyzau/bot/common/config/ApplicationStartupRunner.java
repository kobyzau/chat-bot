package by.kobyzau.bot.common.config;

import by.kobyzau.bot.common.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

  @Autowired private Environment env;

  @Autowired private List<Bot> bots;

  @Override
  public void run(ApplicationArguments args) {
    bots.forEach(Bot::connect);
  }
}
