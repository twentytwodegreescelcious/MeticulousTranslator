package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import java.util.List;

public class Updates {
    List<Result> result;
    private boolean ok;

    public List<Result> getResult() {
        return result;
    }

    public boolean isOk() {
        return ok;
    }
}
