package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
public class AddCommand implements BotCommand {

    private UpdateService updateService;
    private int chatId;
    private String text;

    public AddCommand(int chatId, String text) {
        this.updateService = new UpdateServiceImpl();
        this.chatId = chatId;
        this.text = text;
    }

    @Override
    public void execute() {
        this.updateService.sendMessage(chatId, "This should not appear since it's not finished."); //TODO
    }
}
