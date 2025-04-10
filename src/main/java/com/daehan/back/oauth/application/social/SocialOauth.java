package com.daehan.back.oauth.application.social;

import com.daehan.back.oauth.application.dto.response.GoogleTokenResponse;
import com.daehan.back.oauth.application.dto.response.GoogleUserInfoResponse;
import com.daehan.back.oauth.helper.constant.SocialLoginType;

public interface SocialOauth {
    String getOauthRedirectURL();
    GoogleTokenResponse requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
//        } else if (this instanceof NaverOauth) {
//            return SocialLoginType.NAVER;
//        } else if (this instanceof KakaoOauth) {
//            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }

    GoogleUserInfoResponse getUserInfo(String accessToken);
}
