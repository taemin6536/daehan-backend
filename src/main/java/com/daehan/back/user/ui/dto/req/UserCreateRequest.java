package com.daehan.back.user.ui.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty(message = "이름은 필수 입력 값입니다.")
        String name,
        @NotBlank(message = "패스워드는 필수 입력 값입니다.")
        String password,
        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email,
        @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
        String phoneNumber
){
}
