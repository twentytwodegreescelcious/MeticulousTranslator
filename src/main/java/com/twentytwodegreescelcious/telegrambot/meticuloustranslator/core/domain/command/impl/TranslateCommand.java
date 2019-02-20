package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/8/2019.
 */
@Deprecated
public class TranslateCommand implements BotCommand {
    private UpdateService updateService;
    private int chatId;
    private String text;

    public TranslateCommand(int chatId, String text) {
        this.updateService = new UpdateServiceImpl();
        this.chatId = chatId;
        this.text = text;
    }

    @Override
    public void execute() {
        this.updateService.sendMessage(chatId, text);
    }
}
