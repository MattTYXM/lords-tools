package com.mbss.lordsmobile.tools.redemption.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Matcher;

import static com.mbss.lordsmobile.tools.Constants.LANGUAGE;
import static com.mbss.lordsmobile.tools.Constants.REDEMPTION_USER_INFO_CLIENT_RESPONSE_MESSAGE_PATTERN;
import static java.lang.Integer.parseInt;

@Component
public class RedemptionUserInfoClient extends RedemptionClient<RedemptionUserInfoRequest, RedemptionUserInfoResponse> {

    private static final String AC = "get_extra_info";

    public RedemptionUserInfoClient(
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
    protected MultiValueMap<String, String> getClientRequest(final RedemptionUserInfoRequest getUserInfoRequest) {
        final Map<String, String> clientRequestMap = ImmutableMap.<String, String>builder()
                .put("ac", getAc())
                .put("charname", getUserInfoRequest.getPlayerName())
                .put("lang", LANGUAGE)
                .build();

        final MultiValueMap<String, String> clientRequest = new LinkedMultiValueMap<>();
        clientRequest.setAll(clientRequestMap);

        return clientRequest;
    }

    @Override
    protected RedemptionUserInfoResponse getResponse(final ClientResponse clientResponse) {
        if (clientResponse.getSuccess() != 1) {
            throw new IllegalStateException(clientResponse.getMessage());
        }

        final Matcher matcher = REDEMPTION_USER_INFO_CLIENT_RESPONSE_MESSAGE_PATTERN.matcher(
                clientResponse.getMessage()
        );

        if (!matcher.matches()) {
            throw new IllegalStateException("message is in unexpected format");
        }

        final String kingdomString = matcher.group("kingdom");
        final int kingdom = parseInt(kingdomString);

        final String mightString = matcher.group("might").replaceAll(",", "");
        final int might = parseInt(mightString);

        return RedemptionUserInfoResponse.builder()
                .withKingdom(kingdom)
                .withMight(might)
                .build();
    }
}
