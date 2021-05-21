package com.mbss.lordsmobile.tools.redemption.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbss.lordsmobile.tools.redemption.client.model.RedemptionClientResponse;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RedemptionClientTest {

    private static final String RESPONSE_JSON = "{\"msg\":\"Kingdom: #800<br>Might: 549,936,248\",\"succ\":1}";

    @Test
    void map_response() throws JsonProcessingException {
        final RedemptionClientResponse redemptionClientResponse = new ObjectMapper().readValue(
            RESPONSE_JSON,
            RedemptionClientResponse.class
        );

        final RedemptionResponse redemptionResponse = RedemptionClient.map(redemptionClientResponse);

        assertThat(redemptionResponse.getKingdom()).isEqualTo(800);
        assertThat(redemptionResponse.getMight()).isEqualTo(549936248);
    }
}