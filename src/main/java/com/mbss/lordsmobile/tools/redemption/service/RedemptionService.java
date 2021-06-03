package com.mbss.lordsmobile.tools.redemption.service;

import com.mbss.lordsmobile.tools.redemption.client.RedemptionRedeemCodeClient;
import com.mbss.lordsmobile.tools.redemption.client.RedemptionUserInfoClient;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRedeemCodeRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class RedemptionService {

    private final RedemptionUserInfoClient redemptionUserInfoClient;
    private final RedemptionRedeemCodeClient redemptionRedeemCodeClient;

    public RedemptionService(
            final RedemptionUserInfoClient redemptionUserInfoClient,
            final RedemptionRedeemCodeClient redemptionRedeemCodeClient
    ) {
        this.redemptionUserInfoClient = redemptionUserInfoClient;
        this.redemptionRedeemCodeClient = redemptionRedeemCodeClient;
    }

    public RedemptionUserInfoResponse getUserInfo(
            final RedemptionUserInfoRequest redemptionUserInfoRequest
    ) {
        return redemptionUserInfoClient.doRequest(redemptionUserInfoRequest);
    }

    public boolean redeemCode(
            final RedemptionRedeemCodeRequest redemptionRedeemCodeRequest
    ) {
        return redemptionRedeemCodeClient.doRequest(redemptionRedeemCodeRequest);
    }
}
