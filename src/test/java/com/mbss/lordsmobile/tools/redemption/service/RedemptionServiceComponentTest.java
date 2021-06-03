package com.mbss.lordsmobile.tools.redemption.service;

import com.mbss.lordsmobile.tools.Application;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRedeemCodeRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionRedeemCodeResponse;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoRequest;
import com.mbss.lordsmobile.tools.redemption.model.RedemptionUserInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static com.mbss.lordsmobile.tools.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class RedemptionServiceComponentTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedemptionService redemptionService;

    MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        mockRestServiceServer.reset();
    }

    @Test
    void test_getUserInfo_success() {
        mockRestServiceServer.expect(
                requestTo("https://lordsmobile.igg.com/project/gifts/ajax.php")
        )
                .andExpect(method(POST))
                .andRespond(
                        withStatus(OK)
                                .contentType(TEXT_HTML)
                                .body(GET_REDEMPTION_USER_INFO_RESPONSE_JSON)
                );

        final RedemptionUserInfoResponse userInfoResponse = redemptionService.getUserInfo(
                RedemptionUserInfoRequest.builder()
                        .withPlayerName("SomePlayer")
                        .build()
        );

        assertThat(userInfoResponse)
                .isEqualTo(
                        RedemptionUserInfoResponse.builder()
                                .withKingdom(800)
                                .withMight(1_000_111_999)
                                .build()
                );
    }

    @Test
    void test_getUserInfo_playerNotFound() {
        mockRestServiceServer.expect(
                requestTo("https://lordsmobile.igg.com/project/gifts/ajax.php")
        )
                .andExpect(method(POST))
                .andRespond(
                        withStatus(OK)
                                .contentType(TEXT_HTML)
                                .body(GET_REDEMPTION_USER_INFO_PLAYER_NOT_FOUND_RESPONSE_JSON)
                );

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> redemptionService.getUserInfo(
                        RedemptionUserInfoRequest.builder()
                                .withPlayerName("SomePlayer")
                                .build()
                        )
                )
                .withMessage("This Player Name does not exist!");
    }

    @Test
    void test_redeemCode_success() {
        mockRestServiceServer.expect(
                requestTo("https://lordsmobile.igg.com/project/gifts/ajax.php")
        )
                .andExpect(method(POST))
                .andRespond(
                        withStatus(OK)
                                .contentType(TEXT_HTML)
                                .body(GET_REDEMPTION_REDEEM_CODE_RESPONSE_JSON)
                );

        final RedemptionRedeemCodeResponse redeemCodeResponse = redemptionService.redeemCode(
                RedemptionRedeemCodeRequest.builder()
                        .withPlayerName("SomePlayer")
                        .withCode("somecode")
                        .build()
        );

        assertThat(redeemCodeResponse)
                .isEqualTo(
                        RedemptionRedeemCodeResponse.builder()
                                .withSuccess(true)
                                .withMessage("Some Text Here")
                                .build()
                );
    }

    @Test
    void test_redeemCode_playerNotFound() {
        mockRestServiceServer.expect(
                requestTo("https://lordsmobile.igg.com/project/gifts/ajax.php")
        )
                .andExpect(method(POST))
                .andRespond(
                        withStatus(OK)
                                .contentType(TEXT_HTML)
                                .body(GET_REDEMPTION_REDEEM_CODE_PLAYER_NOT_FOUND_RESPONSE_JSON)
                );

        final RedemptionRedeemCodeResponse redeemCodeResponse = redemptionService.redeemCode(
                RedemptionRedeemCodeRequest.builder()
                        .withPlayerName("SomePlayer")
                        .withCode("somecode")
                        .build()
        );

        assertThat(redeemCodeResponse)
                .isEqualTo(
                        RedemptionRedeemCodeResponse.builder()
                                .withSuccess(false)
                                .withMessage("This Player Name does not exist!")
                                .build()
                );
    }
}