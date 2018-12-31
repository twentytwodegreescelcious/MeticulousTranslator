package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public interface UpdateHandler {

    void onUpdate(Updates result);
    void sendMessage(Integer chatId, String text);
    HttpResponse<JsonNode> getUpdates(String token, int offset) throws UnirestException;

}
