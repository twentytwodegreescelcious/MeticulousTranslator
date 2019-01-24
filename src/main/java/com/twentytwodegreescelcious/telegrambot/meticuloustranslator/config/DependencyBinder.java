package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.config;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UpdateService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation.UpdateServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UpdateServiceImpl.class).to(UpdateService.class).in(Singleton.class);
    }
}
