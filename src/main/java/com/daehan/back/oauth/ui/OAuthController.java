package com.daehan.back.oauth.ui;


import com.daehan.back.oauth.application.service.OAuthService;
import com.daehan.back.oauth.helper.constant.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oauthService;

    @GetMapping("/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") final SocialLoginType socialLoginType
    ) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    @GetMapping("/{socialLoginType}/callback")
    public String callback(
            @PathVariable(name = "socialLoginType") final SocialLoginType socialLoginType,
            @RequestParam(name = "code") final String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.requestAccessToken(socialLoginType, code);
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
