package com.daehan.back.auth.application.service;

import com.daehan.back.auth.application.dto.LoginCommand;
import com.daehan.back.auth.exception.PasswordNotMatch;
import com.daehan.back.auth.exception.UserNotFoundByEmail;
import com.daehan.back.common.jwt.JwtTokenProvider;
import com.daehan.back.common.role.UserRole;
import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        // 사용자에게 부여된 권한을 Enum을 통해 동적으로 가져옴
        List<GrantedAuthority> authorities = getAuthoritiesForUser(user);

        //토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail(), authorities);
        log.info("토큰 생성 완료 => {}", token);

        return token;
    }

    private List<GrantedAuthority> getAuthoritiesForUser(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 권한을 Enum으로 체크하여 권한을 리스트에 추가
        if (user.hasRole(UserRole.ROLE_ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
        } else if (user.hasRole(UserRole.ROLE_USER)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()));
        }

        return authorities;
    }
}
