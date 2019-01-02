package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class UpdateHandlerImpl implements UpdateHandler {

    private final static String ENDPOINT = "https://api.telegram.org/";

    @Override
    public void sendMessage(Integer chatId, String text) {
        new BotMessage(chatId, text).send();
    }

    @Override
    public HttpResponse<JsonNode> getUpdates(String token, int offset) throws UnirestException {
        return Unirest.post(ENDPOINT + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }

}
