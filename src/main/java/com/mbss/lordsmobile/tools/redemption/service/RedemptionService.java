package com.mbss.lordsmobile.tools.redemption.service;

import com.mbss.lordsmobile.tools.redemption.client.RedemptionClient;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionResponse;
import org.springframework.stereotype.Service;

@Service
public class RedemptionService {

    private final RedemptionClient redemptionClient;

    public RedemptionService(final RedemptionClient redemptionClient) {
        this.redemptionClient = redemptionClient;
    }

    public RedemptionResponse redeem(final RedemptionRequest redemptionRequest) {
        return redemptionClient.redeem(redemptionRequest);
    }
}
