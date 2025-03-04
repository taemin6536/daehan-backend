package com.daehan.back.auth.ui.mapper;

import com.daehan.back.auth.application.dto.LoginCommand;
import com.daehan.back.auth.ui.dto.req.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginCommand toCommand(LoginRequest request) {
        return new LoginCommand(
                request.email(),
                request.password()
        );
    }

}
