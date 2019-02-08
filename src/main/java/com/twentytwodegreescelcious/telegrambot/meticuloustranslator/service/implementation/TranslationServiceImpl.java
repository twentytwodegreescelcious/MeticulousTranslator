package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslationService;
import org.json.JSONArray;

import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by twentytwodegreescelcious on 1/8/2019.
 */
@Singleton
public class TranslationServiceImpl implements TranslationService {

    private String word ="";

    @Override
    public String translate(String query, String defaultLanguage) throws IOException {
        String sourceLanguage = "auto";
        String targetLanguage = defaultLanguage;
        parseQuery(query, 1);
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + sourceLanguage +
                "&tl=" + targetLanguage +
                "&dt=t&q=" + word.replaceAll(" ", "%20");
        URI uri = URI.create(url);
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
        Response response = ClientBuilder.newClient()
                .target(uri)
                .request(MediaType.APPLICATION_JSON + ";charset=utf-16")
                .property("User-Agent", "Chrome/71.0.3578.98")
                .get();
        String responseString = response.readEntity(String.class);
         return parseResult(responseString);
    }

    private void parseQuery(String query, int wordPosition) {
        String[] arr = query.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i=wordPosition; i< arr.length; i++) {
            sb.append(" " + arr[i]);
        }
        word = sb.toString();
    }

    private String parseResult(String inputJson) {
        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }
}
