package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.ChatBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.FromBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.MessageBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation.ResultBuilderImpl;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.ResultJsonConvertionService;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class ResultJsonConvertionServiceImpl implements ResultJsonConvertionService {

    @Override
    public List<Result> resultsAsList(JSONArray jsonArray) { //fixme
        List<Result> results = new ArrayList<>();
        for (int i = 0; i < jsonArray.length()-1; i++) {
            Result result = new ResultBuilderImpl()
                    .setUpdateId(jsonArray.getJSONObject(i).getInt("update_id"))
                    .setMessage(new MessageBuilderImpl()
                        .setMessageId(jsonArray.getJSONObject(i).getJSONObject("message").getInt("id"))
                        .setDate(new Date(jsonArray.getJSONObject(i).getJSONObject("message").getString("date")))
                        .setChat(new ChatBuilderImpl()
                            .setId(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getInt("chat_id"))
                            .setFirstName(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("first_name"))
                            .setLastName(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("last_name"))
                            .setUsername(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("username"))
                            .setType(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").getString("type"))
                            .build())
                        .setFrom(new FromBuilderImpl()
                            .setId(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getInt("id"))
                            .setBot(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getBoolean("is_bot"))
                            .setFirstName(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("first_name"))
                            .setLastName(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("last_name"))
                            .setUsername(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("username"))
                            .setLanguageCode(jsonArray.getJSONObject(i).getJSONObject("message").getJSONObject("from").getString("language_code"))
                            .build())
                        .setText(jsonArray.getJSONObject(i).getJSONObject("message").getString("text"))
                        .build())
                    .build();

        }
        return results;
    }
}
