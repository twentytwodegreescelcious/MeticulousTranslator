package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
public interface UserQuizService {

    /**
     * Initiates quiz creation and setup
     * @param id chat_id of a user that started a quiz
     * @return a String that contains a message about quiz start status and a first WordPair of the quiz if the
     *      * initiation of a quiz was successful.
     */
    String startQuiz(Integer id, String topic);

    /**
     * Requests a next word on the quiz and saves results of the previous answer.
     * @return a message containing a next word in a quiz.
     */
    String next(User user, String previousAnswer, boolean userInitiated);

    /**
     * Initiates the end of a quiz.
     * @return A String that contains all the information about the quiz.
     */
    String finishQuiz(User user, boolean isForceQuit);

    void validatePreviousAnswer(String previousAnswer, WordPair wp, boolean wasCorrect);

}
