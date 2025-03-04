package com.daehan.back.user.application.service;

import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.application.mapper.UserMapper;
import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.domain.repository.UserRepository;
import com.daehan.back.user.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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

        return save.getId();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
