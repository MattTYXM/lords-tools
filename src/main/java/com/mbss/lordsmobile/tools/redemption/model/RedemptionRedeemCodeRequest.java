package com.mbss.lordsmobile.tools.redemption.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class RedemptionRedeemCodeRequest {

    String playerName;
    String code;
}
