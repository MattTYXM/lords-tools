package com.mbss.lordsmobile.tools.redemption.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RedemptionClientResponse {

    String message;

    int success;

    @JsonCreator
    public RedemptionClientResponse(
        @JsonProperty("msg") final String message,
        @JsonProperty("succ") final int success
    ) {
        this.message = message;
        this.success = success;
    }
}
