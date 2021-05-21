package com.mbss.lordsmobile.tools.redemption.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class RedemptionResponse {

    int kingdom;
    int might;
}
