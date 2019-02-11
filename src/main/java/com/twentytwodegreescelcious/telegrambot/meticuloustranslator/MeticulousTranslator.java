package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.*;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.DictationServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.TranslationServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Languages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Command.*;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao")
@EntityScan("com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo")
@ComponentScan("com.twentytwodegreescelcious.telegrambot.meticuloustranslator")
public class MeticulousTranslator {

    private static final String TOKEN = "bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    private Invoker invoker = new BotCommandExecutor();
    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);
    private UpdateService updateService = new UpdateServiceImpl();

    @SuppressWarnings("squid:S2189")
    private void run() {
        int lastUpdateId = 0;
        List<Result> results = new ArrayList<>();
        while (true) {
            try {
                results = updateService.getUpdates(MeticulousTranslator.TOKEN, lastUpdateId++);
            } catch (UnirestException e) {
                logger.error("Some other error", e);
            }
            if (!results.isEmpty() || !results.equals(Collections.emptyList())) {
                lastUpdateId = (results.get(results.size() - 1).getUpdateId()) + 1;
                for (int i = 0; i < results.size(); i++) {
                    for (Result r : results) {
                        String text = r.getMessage().getText();
                        int chatId = r.getMessage().getChat().getId();
                        String username = r.getMessage().getFrom().getUsername();
                        String defaultLanguage = r.getMessage().getFrom().getLanguageCode();
                        try {
                            parseAndExecuteCommand(text, chatId, username, defaultLanguage);
                        } catch (IOException exc) {
                            logger.error("Error while parsing translation response");
                        }
                    }
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException exc) {
                logger.error("Interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void parseAndExecuteCommand(String text, int chatId, String username, String defaultLanguage)
            throws IOException {
        if (text.contains("/" + greet)) {
            invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                    username)); // commandDao.greet(chatId, text);
        } else if (text.contains("/" + start)) {
            invoker.executeCommand(new StartCommand(chatId, defaultLanguage));
        } else if (text.contains("/" + translate)) {
            invoker.executeCommand(
                    new TranslateCommand(chatId,
                            new TranslationServiceImpl().translate(text, defaultLanguage.substring(0, 1))));
        } else if (text.contains("/" + setlanguage)) {
            invoker.executeCommand(new AddCommand(chatId, new DictationServiceImpl().setLanguage(chatId, text)));
        }
    }

    public static void main(String[] args) {
        System.out.println(Languages.FRENCH.name());

        SpringApplication.run(MeticulousTranslator.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            new MeticulousTranslator().run();
        };
    }

}
