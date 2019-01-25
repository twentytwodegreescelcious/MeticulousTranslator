package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.BotMessage;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.RequestedUpdateConfiguration;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Update;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */
@Singleton
public class UpdateServiceImpl implements UpdateService {

    private final static String ENDPOINT = "https://api.telegram.org/";
    private Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

    @Override
    public void sendMessage(Integer chatId, String text) {
        new BotMessage(chatId, text).send();
    }

    @Override
    public List<Result> getUpdates(String token, int offset) {
        Response res = HttpClient.POST(ENDPOINT + token + "/getUpdates", new RequestedUpdateConfiguration(offset));
        String responseString = res.readEntity(String.class);
        if (res.getStatus() == 200) {
            JSONObject jsonResponseObject = new JSONObject(responseString);
            JSONArray responses = jsonResponseObject.getJSONArray("result");
            List<Result> results = new ArrayList<>();
            try {
                results = new Update().parseResults(responses);
            } catch (IOException exc) {
                logger.error("Error while parsing JSON", exc);
            }
            return results;
        } else {
            logger.error("Getting updates failed, status: " + res.getStatus());
        }
        return Collections.emptyList();
    }

}
