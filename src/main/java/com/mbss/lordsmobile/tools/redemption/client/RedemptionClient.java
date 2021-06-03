package com.mbss.lordsmobile.tools.redemption.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbss.lordsmobile.tools.client.AbstractClient;
import lombok.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class RedemptionClient<REQUEST, RESPONSE> extends AbstractClient<MultiValueMap<String, String>, REQUEST, RESPONSE> {

    private static final String REDEMPTION_PATH = "/project/gifts/ajax.php";

    protected RedemptionClient(
            final RestTemplate restTemplate,
            final ObjectMapper objectMapper
    ) {
        super(restTemplate, objectMapper);
    }

    @Override
    protected final String getPath() {
        return REDEMPTION_PATH;
    }

    @Override
    protected final RESPONSE getResponse(final String responseBodyString) {
        final ClientResponse clientResponse;
        try {
            clientResponse = objectMapper.readValue(
                    responseBodyString,
                    ClientResponse.class
            );
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException("malformed response: " + responseBodyString);
        }

        return getResponse(clientResponse);
    }

    protected abstract RESPONSE getResponse(final ClientResponse clientResponse);

    @Value
    protected static class ClientResponse {

        String message;
        int success;

        @JsonCreator
        public ClientResponse(
                @JsonProperty("msg") final String message,
                @JsonProperty("succ") final int success
        ) {
            this.message = message;
            this.success = success;
        }
    }
}
