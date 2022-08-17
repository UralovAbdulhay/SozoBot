package asia.sozo.sozobot;

import asia.sozo.sozobot.bots.SozoBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class SozoBotApplication {

    public static void main(String[] args) {

//        ApiContextInitializer.init();


        SpringApplication.run(SozoBotApplication.class, args);
    }

}
