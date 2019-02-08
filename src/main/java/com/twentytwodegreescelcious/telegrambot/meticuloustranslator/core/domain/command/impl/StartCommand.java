package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.DictationServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;

/**
 * Created by twentytwodegreescelcious on 1/3/2019.
 */
public class StartCommand implements BotCommand {

    private UpdateService updateService;
    private int chatId;
    private String defaultLanguage;
    private static final String TEXT = "Welcome! Your language is set to: ";

    public StartCommand(Integer chatId, String defaultLanguage) {
        this.updateService = new UpdateServiceImpl();
        this.defaultLanguage = defaultLanguage;
        this.chatId = chatId;
    }

    @Override
    public void execute() {
        new DictationServiceImpl().register(chatId, defaultLanguage);
        this.updateService.sendMessage(chatId, TEXT + defaultLanguage);
    }
}
