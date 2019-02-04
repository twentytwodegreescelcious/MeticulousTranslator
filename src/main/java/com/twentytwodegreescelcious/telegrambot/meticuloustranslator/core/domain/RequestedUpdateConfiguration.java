package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by twentytwodegreescelcious on 1/24/2019.
 */
public class RequestedUpdateConfiguration {

    @JsonProperty(value = "offset")
    private Integer offset;
    @JsonProperty(value = "limit")
    private Integer limit;
    @JsonProperty(value = "timeout")
    private Integer timeout;
    @JsonProperty(value = "allowed_updates")
    private String[] allowedUpdates;

    public RequestedUpdateConfiguration(Integer offset) {
        this.offset = offset;
        this.limit = null;
        this.timeout = null;
        this.allowedUpdates = null;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String[] getAllowedUpdates() {
        return allowedUpdates;
    }

    public void setAllowedUpdates(String[] allowedUpdates) {
        this.allowedUpdates = allowedUpdates;
    }
}
