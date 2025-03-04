package com.daehan.back.user.ui.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        String name,
        @NotBlank(message = "패스워드는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 최소 8자, 하나 이상의 문자, 숫자, 특수 문자를 포함해야 합니다.")
        String password,
        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email,
        @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
        String phoneNumber
){
}
