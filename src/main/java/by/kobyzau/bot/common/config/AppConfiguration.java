package by.kobyzau.bot.common.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class AppConfiguration {

  @Bean("botUpdateExecutor")
  public Executor getBotUpdateExecutor() {
    ThreadFactory threadFactory =
        new ThreadFactoryBuilder().setNameFormat("my-cached-thread-%d").build();
    return Executors.newCachedThreadPool(threadFactory);
  }

  @Bean("taskExecutor")
  public Executor getTaskExecutor() {
    ThreadFactory threadFactory =
        new ThreadFactoryBuilder().setNameFormat("my-cached-thread-%d").build();
    return Executors.newFixedThreadPool(5, threadFactory);
  }
}
