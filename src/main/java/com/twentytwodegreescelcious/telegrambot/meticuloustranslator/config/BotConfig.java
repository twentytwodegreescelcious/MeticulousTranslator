package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.config;

import org.glassfish.jersey.server.ResourceConfig;

public class BotConfig extends ResourceConfig {

    public BotConfig() {
        register(new DependencyBinder());
        packages(true, "com.twentytwodegreescelcious.telegrambot.meticuloustranslator");
    }
}
