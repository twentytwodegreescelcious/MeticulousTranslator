package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.BotCommandExecutor;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.GreetingsCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.StartCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.TranslateCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.TranslationServiceImpl;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */

public class MeticulousTranslator {

    private final String token;

    private Invoker invoker = new BotCommandExecutor();
    private static Logger logger = LoggerFactory.getLogger(MeticulousTranslator.class);

    @Inject
    private UpdateHandler updateHandler = new UpdateHandlerImpl();

    public MeticulousTranslator(String token) {
        this.token = token;
    }

    public void run() throws UnirestException, InterruptedException {
        int lastUpdateId = 0;
        HttpResponse<JsonNode> response;
        while (true) {
            response = updateHandler.getUpdates(this.token, lastUpdateId++);
            if (response.getStatus() == 200) {
                JSONArray responses = response.getBody().getObject().getJSONArray("result");
                List<Result> results = new ArrayList<>();
                try {
                    results = new ObjectMapper().readValue(responses.toString(), new TypeReference<List<Result>>(){});
                } catch (IOException exc) {
                    logger.error("Error while parsing JSON", exc);
                }
                if (results.equals(Collections.emptyList())) {
                    continue;
                } else {
                    lastUpdateId = (results.get(results.size() - 1).getUpdateId()) + 1;
                }
                for (int i = 0; i < results.size(); i++) {
                    String text = results.get(i).getMessage().getText();
                    int chatId = results.get(i).getMessage().getChat().getId();
                    String username = results.get(i).getMessage().getFrom().getUsername();
                    String defaultLanguage = results.get(i).getMessage().getFrom().getLanguageCode();
                    parseAndExecuteCommand(text, chatId, username, defaultLanguage);
                }
            }
            Thread.sleep(200);
        }
    }

    private void parseAndExecuteCommand(String text, int chatId, String username, String defaultLanguage) {
        if (text.contains("/greet")) {
            invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                    username)); // commandDao.greet(chatId, text);
        } else if (text.contains("/start")) {
            invoker.executeCommand(new StartCommand(chatId));
        } else if (text.contains("/translate")) {
            try {
                invoker.executeCommand(new TranslateCommand(chatId, new TranslationServiceImpl().translate(text, defaultLanguage.substring(0,1))));
            } catch (IOException exc) {
                logger.error("Error while translating", exc);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new MeticulousTranslator("bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk").run();
        } catch (UnirestException e) {
            logger.error("Some other error", e);
        }  catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }
}
