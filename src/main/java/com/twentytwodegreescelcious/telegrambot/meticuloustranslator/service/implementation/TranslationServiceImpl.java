package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslationService;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by twentytwodegreescelcious on 1/8/2019.
 */
public class TranslationServiceImpl implements TranslationService {

    private String sourceLanguage;
    private String targetLanguage;
    private String word ="";

    @Override
    public String translate(String query, String defaultLanguage) throws IOException {
        sourceLanguage = "auto";
        targetLanguage = defaultLanguage;
        parseQuery(query, 1);
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + sourceLanguage +
                "&tl=" + targetLanguage +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private void parseQuery(String query, int wordPosition) {
        /*
        Query example: /translate en ru Hello
        sourceLanguage: en
        targetLanguage: ru
        word: Hello
         */
        String[] arr = query.split(" ");
        if (sourceLanguage == null) {
            sourceLanguage = arr[1];
        }
        if (targetLanguage == null) {
            targetLanguage = arr[2];
        }
        for (int i=wordPosition; i< arr.length; i++) {
            word += " " + arr[i];
        }
    }

    private String parseResult(String inputJson) {
        /*
         * inputJson for word 'hello' translated to language Hindi from English-
         * [[["नमस्ते","hello",,,1]],,"en"]
         * We have to get 'नमस्ते ' from this json.
         */

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }
}
