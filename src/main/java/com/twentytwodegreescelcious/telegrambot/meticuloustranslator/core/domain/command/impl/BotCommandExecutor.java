package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.Invoker;

/**
 * Created by twentytwodegreescelcious on 1/2/2019.
 */
public class BotCommandExecutor implements Invoker {

    @Override
    public void executeCommand(BotCommand botCommand) {
        botCommand.execute();
    }
}
