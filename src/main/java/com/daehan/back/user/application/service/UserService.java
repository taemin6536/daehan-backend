package com.daehan.back.user.application.service;

import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.application.mapper.UserMapper;
import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Long createUser(
            final UserCreateCommand command
    ) {
        User user = userMapper.toUser(command);
        final User save = userRepository.save(user);

        return save.getId();
    }


}
