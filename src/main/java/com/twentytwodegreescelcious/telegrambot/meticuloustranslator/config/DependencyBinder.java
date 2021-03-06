package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.config;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandlerImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UpdateHandlerImpl.class).to(UpdateHandler.class).in(Singleton.class);
    }
}
