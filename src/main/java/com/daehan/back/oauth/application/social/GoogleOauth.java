package com.daehan.back.oauth.application.social;

import com.daehan.back.oauth.application.dto.response.GoogleTokenResponse;
import com.daehan.back.oauth.application.dto.response.GoogleUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {
    private static final Logger log = LoggerFactory.getLogger(GoogleOauth.class);
    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        log.info("redirect_uri : {}", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public GoogleTokenResponse requestAccessToken(final String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = Map.of(
                "code", code,
                "client_id", GOOGLE_SNS_CLIENT_ID,
                "client_secret", GOOGLE_SNS_CLIENT_SECRET,
                "redirect_uri", GOOGLE_SNS_CALLBACK_URL,
                "grant_type", "authorization_code"
        );

        ResponseEntity<GoogleTokenResponse> responseEntity = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, GoogleTokenResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("구글 액세스 토큰 응답: {}", responseEntity.getBody());
            return responseEntity.getBody();
        }
        log.error("구글 액세스 토큰 요청 실패: {}", responseEntity.getStatusCode());
        throw new RuntimeException("구글 액세스 토큰 요청 실패");
    }

    @Override
    public GoogleUserInfoResponse getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 구글 사용자 정보 엔드포인트 호출
        RequestEntity<Void> requestEntity = RequestEntity
                .get(URI.create("https://openidconnect.googleapis.com/v1/userinfo"))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        ResponseEntity<GoogleUserInfoResponse> response = restTemplate.exchange(
                requestEntity,
                GoogleUserInfoResponse.class
        );
        log.info("구글 사용자 정보 응답: {}", response.getBody());
        return response.getBody();
    }
}
