package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.*;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
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

    private Invoker invoker = new BotCommandExecutor();
    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);

    @Value("${predefined.availablelanguages}")
    private String availableLanguages;

    @Value("${meticuloustranslator.token}")
    private String token;

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
                results = updateService.getUpdates(this.token, lastUpdateId++);
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
            User user = userService.getMTUser(chatId);
            if (null != user) {
                defaultLanguage = user.getDefaultLanguage();
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
        if (text.contains(greet.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                    username)); // TODO transform Command pattern to DAO pattern
        } else if (text.contains(start.value)) {
            invoker.executeCommand(new StartCommand(chatId, defaultLanguage));
        } else if (text.contains("/" + translate)) {
            invoker.executeCommand(
                    new TranslateCommand(chatId,
                            translationService.translate(text, defaultLanguage.substring(0, 2))));
        } else if (text.contains(setlanguage.value)) {
            invoker.executeCommand(new AddCommand(chatId, userService.setLanguage(chatId, text)));
        } else if (text.contains(availablelanguages.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId, availableLanguages));
        } else if (text.contains(newtopic.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId,
                    userService.newTopic(chatId, text.substring(newtopic.value.length()).trim())));
        } else if (text.contains(closetopic.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId, userService.closeTopic(chatId)));
        } else if (text.contains(addword.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId,
                    wordPairService.createWordPair(chatId, text.substring(addword.value.length()))));
        } else if (text.contains(getwordsfortopic.value)) {
            invoker.executeCommand(new GreetingsCommand(chatId,
                    wordPairService.getWordPairs(text.substring(getwordsfortopic.value.length()).trim())));
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(MeticulousTranslator.class, args);
    }

}
