package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class HttpClient {

    public static final Client CLIENT = ClientBuilder.newClient();

    public static <T> T GET(String url, Class<T> response) {
        return CLIENT.target(url)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(response);
    }

    public static Response POST(String url, Object data) {
        Response res = CLIENT.target(url)
                .request(MediaType.APPLICATION_JSON_PATCH_JSON_TYPE)
                .post(Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));
        res.readEntity(Object.class);
        System.out.println(res.getStatus());
        return res;
    }
}
