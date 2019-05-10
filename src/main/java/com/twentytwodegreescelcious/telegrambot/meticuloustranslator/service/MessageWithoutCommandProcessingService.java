package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

/**
 * Created by twentytwodegreescelcious on 5/8/2019.
 */
public interface MessageWithoutCommandProcessingService {

    /**
     * This method is called when a user sends a message that contains no command.
     * @param id Identifier of a User.
     * @param message An actual message that has been sent.
     * @return A result of a corresponding service' method or an error message.
     */
    String process(Integer id, String message);
}
