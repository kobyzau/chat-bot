package by.kobyzau.bot.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Profile("prod")
public class Application {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Common TG Bot";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
