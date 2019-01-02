package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Chat;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.From;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.ChatBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.FromBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.MessageBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.ResultBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.ResultJsonConvertionService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class ResultJsonConvertionServiceImpl implements ResultJsonConvertionService {

    @Override
    public List<Result> resultsAsList(JSONArray jsonArray) {
        List<Result> results = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject update = jsonArray.getJSONObject(i);
            Result result = buildResult(update);
            results.add(result);
        }
        return results;
    }

    private Result buildResult(JSONObject update) {
        return new ResultBuilderImpl()
                .setUpdateId(update.getInt("update_id"))
                .setMessage(buildMessage(update))
                .build();
    }

    private Message buildMessage(JSONObject update){
        JSONObject message = update.getJSONObject("message");
        return new MessageBuilderImpl()
                .setMessageId(message.getInt("message_id"))
                .setDate(new Date(message.getInt("date")))
                .setText(message.getString("text"))
                .setFrom(buildFrom(message))
                .setChat(buildChat(message))
                .build();
    }

    private Chat buildChat(JSONObject message) {
        JSONObject chat = message.getJSONObject("chat");
        return new ChatBuilderImpl()
                .setId(chat.getInt("id"))
                .setFirstName(chat.getString("first_name"))
                .setLastName(chat.getString("last_name"))
                .setUsername(chat.getString("username"))
                .setType(chat.getString("type"))
                .build();
    }

    private From buildFrom(JSONObject message) {
        JSONObject from = message.getJSONObject("from");
        return new FromBuilderImpl()
                .setId(from.getInt("id"))
                .setBot(from.getBoolean("is_bot"))
                .setFirstName(from.getString("first_name"))
                .setLastName(from.getString("last_name"))
                .setUsername(from.getString("username"))
                .setLanguageCode(from.getString("language_code"))
                .build();
    }
}
