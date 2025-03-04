package com.daehan.back.user.application.dto;

public record UserCreateCommand(
        String id,
        String name,
        String password,
        String email,
        String phoneNumber
) {
}
