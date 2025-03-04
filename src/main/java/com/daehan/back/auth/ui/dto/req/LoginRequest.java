package com.daehan.back.auth.ui.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email,

        @NotBlank(message = "패스워드는 필수 입력 값입니다.")
        String password
) {
}
