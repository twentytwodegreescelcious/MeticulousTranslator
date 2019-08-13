package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.CommandService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.MessageWithoutCommandProcessingService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslationService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UserQuizService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

import javax.ws.rs.ProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Command.*;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */

@SpringBootApplication
public class MeticulousTranslator implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);

    @Value("${predefined.availablelanguages}")
    private String availableLanguages;

    @Value("${meticuloustranslator.token}")
    private String token;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WordPairService wordPairService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private UserQuizService userQuizService;

    @Autowired
    private MessageWithoutCommandProcessingService messageWithoutCommandProcessingService;

    @SuppressWarnings("squid:S2189")
    @Override
    public void run(String... strings) {
        int lastUpdateId = 0;
        List<Result> results = new ArrayList<>();
        while (true) {
            results = updateService.getUpdates(this.token, lastUpdateId++);
            if (CollectionUtils.isNotEmpty(results)) {
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
            User user = userService.getUser(chatId);
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
        if (text.startsWith(greet.value)) {
            commandService.executeCommand(chatId, messageSource.getMessage("command.greetings",
                    new Object[]{username}, new Locale(userService.getUser(chatId).getDefaultLanguage())));
        } else if (text.startsWith(startquiz.value)) {
            commandService.executeCommand(chatId, userQuizService.startQuiz(chatId,
                    text.substring(startquiz.value.length())));
        } else if (text.startsWith(start.value)) {
            commandService.executeCommand(chatId, messageSource.getMessage("command.start",
                    null, new Locale(defaultLanguage)));
        } else if (text.startsWith(translate.value)) {
            commandService.executeCommand(chatId, translationService.translate(text, defaultLanguage.substring(0, 2)));
        } else if (text.startsWith(setlanguage.value)) {
            commandService.executeCommand(chatId, userService.setLanguage(chatId,
                    text.substring(setlanguage.value.length())));
        } else if (text.startsWith(availablelanguages.value)) {
            commandService.executeCommand(chatId, availableLanguages);
        } else if (text.startsWith(newtopic.value)) {
            commandService.executeCommand(chatId, userService.newTopic(chatId,
                    text.substring(newtopic.value.length()).trim()));
        } else if (text.startsWith(closetopic.value)) {
            commandService.executeCommand(chatId, userService.closeTopic(chatId));
        } else if (text.startsWith(addword.value)) {
            commandService.executeCommand(chatId, wordPairService.createWordPair(chatId,
                    text.substring(addword.value.length())));
        } else if (text.startsWith(getwordsfortopic.value)) {
            commandService.executeCommand(chatId, wordPairService
                    .getWordPairs(text.substring(getwordsfortopic.value.length()).trim()));
        } else if (text.startsWith(next.value)) {
             commandService.executeCommand(chatId, userQuizService.next(userService.getUser(chatId),
                     text.substring(next.value.length()), true));
        } else if (text.startsWith(finishquiz.value)) {
            commandService.executeCommand(chatId, userQuizService
                    .finishQuiz(userService.getUser(chatId), true));
        } else {
            commandService.executeCommand(chatId, messageWithoutCommandProcessingService.process(chatId, text));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MeticulousTranslator.class, args);
    }

}
