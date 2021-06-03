package com.mbss.lordsmobile.tools.redemption.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRedeemCodeRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRedeemCodeResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.mbss.lordsmobile.tools.Constants.LANGUAGE;

@Component
public class RedemptionRedeemCodeClient extends RedemptionClient<RedemptionRedeemCodeRequest, RedemptionRedeemCodeResponse> {

    private static final String AC = "get_gifts";

    public RedemptionRedeemCodeClient(
            final RestTemplate restTemplate,
            final ObjectMapper objectMapper
    ) {
        super(restTemplate, objectMapper);
    }

    @Override
    protected String getAc() {
        return AC;
    }

    @Override
    protected MultiValueMap<String, String> getClientRequest(
            final RedemptionRedeemCodeRequest redemptionRedeemCodeRequest
    ) {
        final Map<String, String> clientRequestMap = ImmutableMap.<String, String>builder()
                .put("ac", getAc())
                .put("type", "1")
                .put("iggid", "0")
                .put("charname", redemptionRedeemCodeRequest.getPlayerName())
                .put("cdkey", redemptionRedeemCodeRequest.getCode())
                .put("lang", LANGUAGE)
                .build();

        final MultiValueMap<String, String> clientRequest = new LinkedMultiValueMap<>();
        clientRequest.setAll(clientRequestMap);

        return clientRequest;
    }

    @Override
    protected RedemptionRedeemCodeResponse getResponse(final ClientResponse clientResponse) {
        return RedemptionRedeemCodeResponse.builder()
                .withSuccess(clientResponse.getSuccess() == 1)
                .withMessage(clientResponse.getMessage())
                .build();
    }
}
