package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class BotMessage {

    @Value("meticuloustranslator.resource")
    private String telegramResource;

    @JsonProperty(value = "chat_id")
    private final int chatId;
    @JsonProperty(value = "text")
    private final String text;

    public BotMessage(int chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public void send() {
        HttpClient.POST(telegramResource + "/sendMessage", this);
    }
}
