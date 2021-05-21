package com.mbss.lordsmobile.tools.redemption.client;

import com.mbss.lordsmobile.tools.redemption.client.model.RedemptionClientRequest;
import com.mbss.lordsmobile.tools.redemption.client.model.RedemptionClientResponse;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionResponse;
import lombok.NonNull;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mbss.lordsmobile.tools.Constants.GAME_ID;
import static com.mbss.lordsmobile.tools.Constants.LANGUAGE;
import static java.lang.Integer.parseInt;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpMethod.POST;

@Component
public class RedemptionClient {

    private static final Pattern REDEMPTION_CLIENT_RESPONSE_MESSAGE_PATTERN = Pattern.compile(
        "(Kingdom:[\\s\\S]#)(?<kingdom>800)(<br>)(Might:[\\s\\S])(?<might>[0-9,]*)");

    private static final String PROJECT_GIFTS_PATH = "/project/gifts/ajax.php";

    private static final String AC_GET_EXTRA_INFO = "get_extra_info";

    private final RestTemplate restTemplate;

    public RedemptionClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RedemptionResponse redeem(final RedemptionRequest redemptionRequest) {
        final RedemptionClientRequest body = map(redemptionRequest);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<RedemptionClientRequest> httpEntity = new HttpEntity<>(body, headers);

        final ResponseEntity<RedemptionClientResponse> responseEntity = restTemplate.exchange(
            PROJECT_GIFTS_PATH,
            POST,
            httpEntity,
            RedemptionClientResponse.class,
            singletonMap("game_id", GAME_ID)
        );

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("unsuccessful status: " + responseEntity.getStatusCode());
        }

        final RedemptionClientResponse redemptionClientResponse = responseEntity.getBody();

        if (redemptionClientResponse.getSuccess() != 1) {
            throw new IllegalStateException("unacceptable success value: " + redemptionClientResponse.getSuccess());
        }

        return map(redemptionClientResponse);
    }

    static RedemptionClientRequest map(final RedemptionRequest source) {
        return RedemptionClientRequest.builder()
            .withAc(AC_GET_EXTRA_INFO)
            .withCharname(source.getPlayerName())
            .withLang(LANGUAGE)
            .build();
    }

    static RedemptionResponse map(@NonNull final RedemptionClientResponse source) {
        final Matcher matcher = REDEMPTION_CLIENT_RESPONSE_MESSAGE_PATTERN.matcher(source.getMessage());

        if (!matcher.matches()) {
            throw new IllegalStateException("RedemptionClientResponse response in unexpected format");
        }

        final String kingdomString = matcher.group("kingdom");
        final int kingdom = parseInt(kingdomString);

        final String mightString = matcher.group("might").replaceAll(",", "");
        final int might = parseInt(mightString);

        return RedemptionResponse.builder()
            .withKingdom(kingdom)
            .withMight(might)
            .build();
    }
}
