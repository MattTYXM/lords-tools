package com.mbss.lordsmobile.tools.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.mbss.lordsmobile.tools.Constants.GAME_ID;
import static com.mbss.lordsmobile.tools.Constants.GAME_ID_KEY;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

public abstract class AbstractClient<CLIENT_REQUEST, REQUEST, RESPONSE> {

    protected final RestTemplate restTemplate;
    protected final ObjectMapper objectMapper;

    protected AbstractClient(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public final RESPONSE doRequest(final REQUEST request) {
        final ResponseEntity<String> responseEntity = getStringResponseEntity(request);

        return getResponse(responseEntity.getBody());
    }

    protected abstract String getPath();

    protected abstract String getAc();

    protected abstract CLIENT_REQUEST getClientRequest(final REQUEST request);

    protected abstract RESPONSE getResponse(final String responseBodyString);

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);

        return httpHeaders;
    }

    private HttpEntity<CLIENT_REQUEST> getHttpEntity(final REQUEST request) {
        return new HttpEntity<>(
                getClientRequest(request),
                getHttpHeaders()
        );
    }

    private ResponseEntity<String> getStringResponseEntity(final REQUEST request) {
        final ResponseEntity<String> responseEntity = restTemplate.exchange(
                getPath(),
                POST,
                getHttpEntity(request),
                String.class,
                singletonMap(GAME_ID_KEY, GAME_ID)
        );

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("unsuccessful status code: " + responseEntity.getStatusCode());
        }

        return responseEntity;
    }
}
