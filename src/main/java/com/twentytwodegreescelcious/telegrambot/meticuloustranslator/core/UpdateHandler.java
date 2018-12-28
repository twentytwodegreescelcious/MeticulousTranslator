package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public interface UpdateHandler {

    void onUpdate(Updates result);
}
