package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command;

/**
 * Created by twentytwodegreescelcious on 1/2/2019.
 */
@Deprecated
@FunctionalInterface
public interface Invoker {
    void executeCommand(BotCommand botCommand);
}
