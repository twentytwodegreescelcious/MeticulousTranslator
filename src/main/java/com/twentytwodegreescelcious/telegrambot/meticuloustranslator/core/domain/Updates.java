package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

@JsonRootName(value = "updates")
public class Updates {
    @JsonProperty(value = "results")
    private List<Result> results;
    @JsonProperty(value = "ok")
    private boolean ok;

    public List<Result> getResults() {
        return results;
    }

    public boolean isOk() {
        return ok;
    }
}
