package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public interface UpdateHandler {

    void sendMessage(Integer chatId, String text);
    List<Result> getUpdates(String token, int offset) throws UnirestException;

}
