package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class Result {

    @JsonProperty(value = "update_id")
    private int updateId;
    @JsonProperty(value = "message")
    private Message message;

    public int getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }
}
