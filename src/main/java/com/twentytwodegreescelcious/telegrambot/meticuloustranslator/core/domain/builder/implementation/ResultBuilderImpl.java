package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.ResultBuilder;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class ResultBuilderImpl implements ResultBuilder {

    private Result result;

    public ResultBuilderImpl() {
        result = new Result();
    }

    @Override
    public Result build() {
        Result result = new Result();
        result.setMessage(this.result.getMessage());
        result.setUpdateId(this.result.getUpdateId());
        return result;
    }

    @Override
    public ResultBuilder setUpdateId(Integer updateId) {
        this.result.setUpdateId(updateId);
        return this;
    }

    @Override
    public ResultBuilder setMessage(Message message) {
        this.result.setMessage(message);
        return this;
    }
}
