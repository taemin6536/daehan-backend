package com.daehan.back.user.application.service;

import com.daehan.back.common.role.UserRole;
import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.application.mapper.UserMapper;
import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.domain.repository.UserRepository;
import com.daehan.back.user.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public Long createUser(
            final UserCreateCommand command
    ) {
        userRepository.findByEmail(command.email())
                .ifPresent(user -> {
                    throw new DuplicateEmailException("이미 존재하는 사용자입니다.");
                });
        User user = userMapper.toUser(command);

        //비밀번호 암호화
        user.passwordEncode(passwordEncoder);
        final User save = userRepository.save(user);

        return save.getSeq();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(
            final Long seq
    ) {
        try {
            //본인 확인 추가해야함.


            User user = userRepository.findBySeq(seq)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AccessDeniedException("인증되지 않은 사용자입니다.");
            }


            user.delete();
            userRepository.save(user);
        } catch (UsernameNotFoundException e) {
            log.error("사용자를 찾을 수 없습니다. SEQ => {}", seq);
            throw e;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 사용자의 권한을 Enum으로 체크하여 권한을 리스트에 추가
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
