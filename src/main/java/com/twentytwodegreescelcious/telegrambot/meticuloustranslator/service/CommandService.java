package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

/**
 * Created by twentytwodegreescelcious on 2/20/2019.
 */
public interface CommandService {

    /**
     * Is a general method that sends response to the telegram user that requested perfoming some command.
     *
     * @param chatId telegram user chat id
     * @param response message for the user
     */
    void executeCommand(Integer chatId, String response);

}
