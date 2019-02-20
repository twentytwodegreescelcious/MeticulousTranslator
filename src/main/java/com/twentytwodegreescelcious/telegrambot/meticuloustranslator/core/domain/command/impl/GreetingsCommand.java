package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/2/2019.
 */
@Deprecated
public class GreetingsCommand implements BotCommand {

    private UpdateService updateService;
    private int chatId;
    private String text;

    public GreetingsCommand(Integer chatId, String text) {
        this.updateService = new UpdateServiceImpl();
        this.chatId = chatId;
        this.text = text;
    }

    @Override
    public void execute() {
        this.updateService.sendMessage(chatId, text);
    }
}
