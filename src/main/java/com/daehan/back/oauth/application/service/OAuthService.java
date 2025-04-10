package com.daehan.back.oauth.application.service;

import com.daehan.back.auth.application.service.AuthService;
import com.daehan.back.oauth.application.dto.response.GoogleTokenResponse;
import com.daehan.back.oauth.application.dto.response.GoogleUserInfoResponse;
import com.daehan.back.oauth.application.social.SocialOauth;
import com.daehan.back.oauth.helper.constant.SocialLoginType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthService authService;
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;

    /**
     * 소셜 로그인 요청 처리. (예: 구글 로그인 버튼 클릭 시)
     */
    public void request(final SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error("구글 로그인 리다이렉션 실패", e);
            throw new RuntimeException("구글 로그인 리다이렉션 중 오류 발생", e);
        }
    }

    /**
     * 소셜 로그인 콜백 처리.
     * 구글로부터 받은 인가 코드(code)를 통해 액세스 토큰을 받고, 사용자 정보를 조회한 후,
     * 내부 인증을 진행하여 JWT 토큰을 발급한다.
     */
    public String handleSocialLogin(final SocialLoginType socialLoginType, final String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);

        // 인가 코드를 통해 구글 토큰 응답 받기
        GoogleTokenResponse tokenResponse = (GoogleTokenResponse) socialOauth.requestAccessToken(code);
        log.info(">> 구글로부터 받은 토큰 :: {}", tokenResponse);

        // 받은 액세스 토큰을 통해 사용자 정보 조회
        GoogleUserInfoResponse userInfo = socialOauth.getUserInfo(tokenResponse.accessToken());

        // 내부 인증: DB에 사용자 존재 여부 확인 후 신규 등록/로그인 처리, 그리고 JWT 발급
        return authService.socialLogin(userInfo);
    }

//    public String requestAccessToken(final SocialLoginType socialLoginType, final String code) {
//        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
//        return socialOauth.requestAccessToken(code);
//    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
