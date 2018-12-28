package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.config;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class BotConfig extends ResourceConfig {

    public BotConfig() {
        register(new DependencyBinder());
        packages(true, "com.twentytwodegreescelcious.telegrambot.meticuloustranslator");
    }
}
