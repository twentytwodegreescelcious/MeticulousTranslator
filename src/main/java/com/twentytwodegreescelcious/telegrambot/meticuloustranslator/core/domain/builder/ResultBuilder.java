package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public interface ResultBuilder {

    Result build();
    ResultBuilder setUpdateId(Integer updateId);
    ResultBuilder setMessage(Message message);
}
