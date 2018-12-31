package com.twentytwodegreescelcious.telegrambot.meticuloustranslator;



import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by twentytwodegreescelcious on 12/28/2018.
 */
public class MeticulousTranslator {

    private  final String token;

    @Inject
    private UpdateHandler updateHandler = new UpdateHandlerImpl();

    public MeticulousTranslator(String token) {
        this.token = token;
    }

    public void run() throws UnirestException {
        long last_update_id = 0L; // last processed command
        HttpResponse<JsonNode> response;
        while (true) {
            response = updateHandler.getUpdates(this.token, last_update_id++);
            if (response.getStatus() == 200) {
                JSONArray responses =   response.getBody().getObject().getJSONArray("result");
                if (responses.isNull(0)) continue;
                else last_update_id = responses
                        .getJSONObject(responses.length() - 1)
                        .getInt("update_id") + 1;
                for (int i = 0; i < responses.length(); i++) {
                    JSONObject message = responses
                            .getJSONObject(i)
                            .getJSONObject("message");
                    int chat_id = message
                            .getJSONObject("chat")
                            .getInt("id");
                    String username = message
                            .getJSONObject("chat")
                            .getString("username");
                    String text = message
                            .getString("text");
                    if (text.contains("/start")) {
                        String reply = "Hi, this is an example bot\n" +
                                "Your chat_id is " + chat_id + "\n" +
                                "Your username is " + username;
                        updateHandler.sendMessage(this.token, chat_id, reply);
                    } else if (text.contains("/echo")) {
                        updateHandler.sendMessage(this.token, chat_id, "Received " + text);
                    } else if (text.contains("/toupper")) {
                        String param = text.substring("/toupper".length(), text.length());
                        updateHandler.sendMessage(this.token, chat_id, param.toUpperCase());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new MeticulousTranslator("bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk").run();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
