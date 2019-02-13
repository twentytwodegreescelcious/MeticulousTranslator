package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.*;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.DictationService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslationService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Command.*;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */

@SpringBootApplication
public class MeticulousTranslator implements CommandLineRunner {

    private static final String TOKEN = "bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    private Invoker invoker = new BotCommandExecutor();
    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);

    @Value("${predefined.availablelanguages}")
    private String availableLanguages;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private DictationService dictationService;

    @Autowired
    private UserService userService;

    @Autowired
    private WordPairService wordPairService;

    @Autowired
    private TranslationService translationService;

    @SuppressWarnings("squid:S2189")
    @Override
    public void run(String... strings) {
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
                processResults(results);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException exc) {
                logger.error("Interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processResults(List<Result> results) {
        for (Result r : results) {
            String text = r.getMessage().getText();
            int chatId = r.getMessage().getChat().getId();
            String username = r.getMessage().getFrom().getUsername();
            String defaultLanguage = r.getMessage().getFrom().getLanguageCode();
            MTUser mtUser = userService.getMTUser(chatId);
            if (null != mtUser) {
                defaultLanguage = mtUser.getDefaultLanguage();
            }
            try {
                parseAndExecuteCommand(text, chatId, username, defaultLanguage);
            } catch (IOException exc) {
                logger.error("Error while parsing translation response");
            }
        }
    }

    private void parseAndExecuteCommand(String text, int chatId, String username, String defaultLanguage)
            throws IOException {
        if (text.contains("/" + greet)) {
            invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                    username)); // TODO transform Command patter to DAO pattern
        } else if (text.contains("/" + start)) {
            invoker.executeCommand(new StartCommand(chatId, defaultLanguage));
        } else if (text.contains("/" + translate)) {
            invoker.executeCommand(
                    new TranslateCommand(chatId,
                            translationService.translate(text, defaultLanguage.substring(0, 2))));
        } else if (text.contains("/" + setlanguage)) {
            invoker.executeCommand(new AddCommand(chatId, userService.setLanguage(chatId, text)));
        } else if (text.contains("/" + availablelanguages)) {
            invoker.executeCommand(new GreetingsCommand(chatId, availableLanguages));
        } else if (text.contains("/" + newtopic)) {
            invoker.executeCommand(new GreetingsCommand(chatId, userService.newTopic(chatId, text.substring(10).trim())));
        } else if (text.contains("/" + closetopic)) {
            invoker.executeCommand(new GreetingsCommand(chatId, userService.closeTopic(chatId)));
        } else if (text.contains("/" + addword)) {
            invoker.executeCommand(new GreetingsCommand(chatId, wordPairService.createWordPair(chatId, text.substring(9))));
        } else if (text.contains("/" + getwordsfortopic)) {
            invoker.executeCommand(new GreetingsCommand(chatId, wordPairService.getWordPairsByTopic(text.substring(18).trim())));
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(MeticulousTranslator.class, args);
    }

}
