package by.kobyzau.bot.common.task;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Profile("prod")
public class PingHerokuTask {

  @Value("${heroku.url}")
  private String herokuURL;

  @Scheduled(fixedRateString = "${task.pingHeroku.delay:60000}")
  public void pingHeroku() {
    try (CloseableHttpClient httpClient = createHttpClient()) {
      HttpGet request = new HttpGet(herokuURL);
      try (CloseableHttpResponse response = httpClient.execute(request)) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 400 && statusCode <= 599) {
          System.out.println("Heroku has bad state: " + statusCode);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private CloseableHttpClient createHttpClient() {
    RequestConfig requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(30 * 1000)
            .setConnectionRequestTimeout(30 * 1000)
            .setSocketTimeout(30 * 1000)
            .build();

    return HttpClientBuilder.create()
        .useSystemProperties()
        .setDefaultRequestConfig(requestConfig)
        .build();
  }
}
