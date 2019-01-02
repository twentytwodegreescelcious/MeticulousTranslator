package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class BotMessage {

    private static final String TELEGRAM_RESOURCE =
            "https://api.telegram.org/bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    private final int chatId;
    private final String text;

    public BotMessage(int chatId, String text) {
        this.chatId = chatId;
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
