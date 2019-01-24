package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Update;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */
@Singleton
public class UpdateHandlerImpl implements UpdateHandler {

    private final static String ENDPOINT = "https://api.telegram.org/";
    private Logger logger = LoggerFactory.getLogger(UpdateHandlerImpl.class);

    @Override
    public void sendMessage(Integer chatId, String text) {
        new BotMessage(chatId, text).send();
    }

    @Override
    public List<Result> getUpdates(String token, int offset) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(ENDPOINT + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
        Response res = HttpClient.POST(ENDPOINT +token+ "/getUpdates", new JsonNode("{offset:" + offset + "}"));

        if (response.getStatus() == 200) {

            JSONArray responses = response.getBody().getObject().getJSONArray("result");
            List<Result> results = new ArrayList<>();
            try {
                results = new Update().parseResults(responses);
            } catch (IOException exc) {
                logger.error("Error while parsing JSON", exc);
            }
            return results;
        }
        return Collections.emptyList();
    }

}
