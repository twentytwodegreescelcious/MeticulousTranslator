package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.*;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.DictationServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.TranslationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Command.*;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */

@SpringBootApplication
@EnableJpaRepositories("com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao")
@EntityScan("com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo")
public class MeticulousTranslator {

    private final String token;

    private Invoker invoker = new BotCommandExecutor();
    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);

    @Inject
    private UpdateService updateService = new UpdateServiceImpl();

    public MeticulousTranslator(String token) {
        this.token = token;
    }

    @SuppressWarnings("squid:S2189")
    private void run() {
        int lastUpdateId = 0;
        List<Result> results = new ArrayList<>();
        while (true) {
            try {
                results = updateService.getUpdates(this.token, lastUpdateId++);
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
                        parseAndExecuteCommand(text, chatId, username, defaultLanguage);
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

    private void parseAndExecuteCommand(String text, int chatId, String username, String defaultLanguage) {
        if (text.contains("/" + greet)) {
            invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                    username)); // commandDao.greet(chatId, text);
        } else if (text.contains("/" + start)) {
            invoker.executeCommand(new StartCommand(chatId, defaultLanguage));
        } else if (text.contains("/" + translate)) {
            try {
                invoker.executeCommand(new TranslateCommand(chatId, new TranslationServiceImpl().translate(text, defaultLanguage.substring(0, 1))));
            } catch (IOException exc) {
                logger.error("Error while translating", exc);
            }
        } else if (text.contains("/" + add)) {

            invoker.executeCommand(new AddCommand(chatId, defaultLanguage));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MeticulousTranslator.class, args);

        new MeticulousTranslator("bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk").run();
    }
}
