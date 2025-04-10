package com.daehan.back.oauth.helper.constant;

public enum SocialLoginType {
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String type;

    SocialLoginType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SocialLoginType from(String type) {
        for (SocialLoginType socialLoginType : values()) {
            if (socialLoginType.getType().equalsIgnoreCase(type)) {
                return socialLoginType;
            }
        }
        throw new IllegalArgumentException("Invalid social login type: " + type);
    }

}
