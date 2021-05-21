package com.mbss.lordsmobile.tools.redemption.client.model;

import lombok.*;

@Value
@Builder(setterPrefix = "with")
public class RedemptionClientRequest {

    @NonNull
    String ac;

    @NonNull
    String charname;

    @NonNull
    String lang;
}
