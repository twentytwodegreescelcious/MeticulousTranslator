package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.web;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.UpdateHandler;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

@Path("api")
public class UpdateController {

    @Inject
    private UpdateHandler updateHandler;


    @Path("result")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void processUpdate(Updates updates) {
        updateHandler.onUpdate(updates);
    }
}
