package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class BotMessage {

    private static final String TELEGRAM_RESOURCE =
            "https://api.telegram.org/bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    private final int chat_id;
    private final String text;

    public BotMessage(int chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
    }

    public int getChat_id() {
        return chat_id;
    }

    public String getText() {
        return text;
    }

    public void send() {
        HttpClient.POST(TELEGRAM_RESOURCE + "/sendMessage", this);
    }
}
