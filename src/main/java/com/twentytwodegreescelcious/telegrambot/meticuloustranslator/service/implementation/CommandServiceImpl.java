package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.CommandService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by twentytwodegreescelcious on 2/20/2019.
 */
@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    private UpdateService updateService;

    @Override
    public void executeCommand(Integer chatId, String response) {
        this.updateService.sendMessage(chatId, response);
    }
}
