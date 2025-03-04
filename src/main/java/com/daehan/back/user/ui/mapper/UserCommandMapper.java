package com.daehan.back.user.ui.mapper;


import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.ui.dto.req.UserCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMapper {
    public UserCreateCommand toCommand(UserCreateRequest request) {
        return new UserCreateCommand(
                request.name(),
                request.password(),
                request.email(),
                request.phoneNumber()
        );
    }



}
