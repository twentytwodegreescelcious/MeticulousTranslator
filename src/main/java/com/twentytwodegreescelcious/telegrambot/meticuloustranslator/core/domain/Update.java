package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 1/18/2019.
 */
public class Update {

    @JsonProperty("ok")
    private boolean ok;
    @JsonProperty("result")
    private List<Result> results;

    public List<Result> getResults() {
        return this.results;
    }
}
