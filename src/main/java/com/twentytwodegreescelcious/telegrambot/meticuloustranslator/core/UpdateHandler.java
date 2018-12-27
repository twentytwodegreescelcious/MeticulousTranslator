package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;

public interface UpdateHandler {

    void onUpdate(Updates result);
}
