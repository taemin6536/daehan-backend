package com.daehan.back.user.application.dto;

public record UserCreateCommand(
        String name,
        String password,
        String email,
        String phoneNumber
) {
}
