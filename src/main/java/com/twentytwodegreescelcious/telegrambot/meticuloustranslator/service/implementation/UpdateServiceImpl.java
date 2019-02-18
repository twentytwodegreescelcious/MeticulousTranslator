package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.BotMessage;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.RequestedUpdateConfiguration;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Update;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */
@Service
public class UpdateServiceImpl implements UpdateService {

    private Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);
    
    @Value("${meticuloustransaltor.endpoint}")
    private String endpoint;

    @Override
    public void sendMessage(Integer chatId, String text) {
        new BotMessage(chatId, text).send();
    }

    @Override
    public List<Result> getUpdates(String token, int offset) {
        Response res = HttpClient.POST(endpoint + token + "/getUpdates", new RequestedUpdateConfiguration(offset));
        Update update = res.readEntity(Update.class);
        if (res.getStatus() == 200) {
            return update.getResults();
        } else {
            logger.error("Getting updates failed, status: " + res.getStatus());
        }
        return Collections.emptyList();
    }

}
