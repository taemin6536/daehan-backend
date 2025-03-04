package com.daehan.back.auth.application.dto;

public record LoginCommand (
        String email,
        String password
){
}
