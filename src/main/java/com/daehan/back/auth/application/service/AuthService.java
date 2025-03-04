package com.daehan.back.auth.application.service;

import com.daehan.back.auth.application.dto.LoginCommand;
import com.daehan.back.auth.exception.PasswordNotMatch;
import com.daehan.back.auth.exception.UserNotFoundByEmail;
import com.daehan.back.common.jwt.JwtTokenProvider;
import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(
            final LoginCommand command
    ) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new UserNotFoundByEmail("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new PasswordNotMatch("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        log.info("토큰 생성 완료 => {}", token);
        return token;
    }
}
