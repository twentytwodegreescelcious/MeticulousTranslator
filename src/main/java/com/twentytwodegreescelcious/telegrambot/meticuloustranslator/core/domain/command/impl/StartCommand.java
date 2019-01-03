package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/3/2019.
 */
public class StartCommand implements BotCommand {

    private UpdateHandler updateHandler;
    private int chatId;
    private static final String text = "I am yours to command.";

    public StartCommand(Integer chatId) {
        this.updateHandler = new UpdateHandlerImpl();
        this.chatId = chatId;
    }

    @Override
    public void execute() {
        this.updateHandler.sendMessage(chatId, text);
    }
}
