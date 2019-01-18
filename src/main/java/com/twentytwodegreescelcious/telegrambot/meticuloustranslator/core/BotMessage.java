package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class BotMessage {

    private static final String TELEGRAM_RESOURCE =
            "https://api.telegram.org/bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    @JsonProperty(value = "chat_id")
    private final int chatId;;
    private final String text;

    public BotMessage(int chat_id, String text) {
        this.chatId = chat_id;
        this.text = text;
    }

    public int getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public void send() {
        HttpClient.POST(TELEGRAM_RESOURCE + "/sendMessage", this);
    }
}
