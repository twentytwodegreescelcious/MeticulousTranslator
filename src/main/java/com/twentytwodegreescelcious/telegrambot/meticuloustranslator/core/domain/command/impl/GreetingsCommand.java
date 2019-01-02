package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.BotMessage;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/2/2019.
 */
public class GreetingsCommand implements BotCommand {

    private UpdateHandler updateHandler;
    private int chatId;
    private String text;

    public GreetingsCommand(Integer chatId, String text) {
        this.updateHandler = new UpdateHandlerImpl();
        this.chatId = chatId;
        this.text = text;
    }

    @Override
    public void execute() {
        this.updateHandler.sendMessage(chatId, text);
    }
}
