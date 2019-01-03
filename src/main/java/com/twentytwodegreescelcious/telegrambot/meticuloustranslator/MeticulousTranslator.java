package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.BotMessage;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.BotCommandExecutor;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.GreetingsCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl.StartCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.ResultJsonConvertionService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.ResultJsonConvertionServiceImpl;
import org.json.JSONArray;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */
public class MeticulousTranslator {

    private final String token;

    private ResultJsonConvertionService jsonConverter = new ResultJsonConvertionServiceImpl();

    private Invoker invoker = new BotCommandExecutor();

    @Inject
    private UpdateHandler updateHandler = new UpdateHandlerImpl();

    public MeticulousTranslator(String token) {
        this.token = token;
    }

    public void run() throws UnirestException {
        int lastUpdateId = 0;
        HttpResponse<JsonNode> response;
        while (true) {
            response = updateHandler.getUpdates(this.token, lastUpdateId++);
            if (response.getStatus() == 200) {
                JSONArray responses = response.getBody().getObject().getJSONArray("result");
//                List<Result> results = jsonConverter.resultsAsList(responses);
                List<Result> results = new ArrayList<>();
                try {
                    results = new ObjectMapper().readValue(responses.toString(), new TypeReference<List<Result>>() {
                    });
                } catch (JsonParseException exc) {
                    System.err.println(exc);
                } catch (JsonMappingException exc) {
                    System.err.println(exc);
                } catch (IOException exc) {
                    System.err.println(exc);
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
                    if (text.contains("/greet")) {
                        invoker.executeCommand(new GreetingsCommand(chatId, "Greetings to you, " +
                                username));
                    } else if (text.contains("/start")) {
                        invoker.executeCommand(new StartCommand(chatId));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new MeticulousTranslator("bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk").run();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
