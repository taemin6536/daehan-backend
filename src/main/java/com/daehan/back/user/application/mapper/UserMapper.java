package com.daehan.back.user.application.mapper;

import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(
            final UserCreateCommand command
    ){
        return User.builder()
                .name(command.name())
                .password(command.password())
                .email(command.email())
                .phoneNumber(command.phoneNumber())
                .build();
    }
}
