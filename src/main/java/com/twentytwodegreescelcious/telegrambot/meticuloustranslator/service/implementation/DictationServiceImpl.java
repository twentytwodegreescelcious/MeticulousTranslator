package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.DictationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Service
public class DictationServiceImpl implements DictationService {

    @Autowired
    private UserService userService;


}
