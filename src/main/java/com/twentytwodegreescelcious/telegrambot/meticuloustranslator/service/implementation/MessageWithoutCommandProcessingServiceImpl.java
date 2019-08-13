package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.MessageWithoutCommandProcessingService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UserQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by twentytwodegreescelcious on 5/8/2019.
 */
@Service
public class MessageWithoutCommandProcessingServiceImpl implements MessageWithoutCommandProcessingService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQuizService userQuizService;

    @Override
    public String process(Integer id, String message) {
        User user = userService.getUser(id);
        if (user != null) {
            if (user.isDictation() && user.getDictationTopic() != null) {
                return userQuizService.next(user, message, false);
            }
            //TODO Return help otherwise
        }
        return "HELP TO BE RETURNED";
    }
}
