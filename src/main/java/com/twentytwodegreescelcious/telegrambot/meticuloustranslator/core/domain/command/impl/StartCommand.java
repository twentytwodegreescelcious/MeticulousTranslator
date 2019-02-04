package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/3/2019.
 */
public class StartCommand implements BotCommand {

    private UpdateService updateService;
    private int chatId;
    private static final String TEXT = "I am yours to command.";

    public StartCommand(Integer chatId) {
        this.updateService = new UpdateServiceImpl();
        this.chatId = chatId;
    }

    @Override
    public void execute() {
        this.updateService.sendMessage(chatId, TEXT);
    }
}
