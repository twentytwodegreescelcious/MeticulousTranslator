package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 1/18/2019.
 */
public class Update {

    private List<Result> results;

    public List<Result> parseResults(JSONArray responses) throws IOException {
        return new ObjectMapper().readValue(responses.toString(), new TypeReference<List<Result>>(){});
    }
}
