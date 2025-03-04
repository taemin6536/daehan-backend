package com.daehan.back.user.ui.dto.req;

import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty String id,
        @NotEmpty String name,
        @NotEmpty String password,
        String email,
        String phoneNumber
){
}
