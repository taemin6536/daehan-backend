package com.daehan.back.auth.ui;

import com.daehan.back.auth.application.dto.LoginCommand;
import com.daehan.back.auth.application.service.AuthService;
import com.daehan.back.auth.ui.dto.req.LoginRequest;
import com.daehan.back.auth.ui.mapper.AuthMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMapper authMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid final LoginRequest request
    ) {
        LoginCommand command = authMapper.toCommand(request);
        String token = authService.login(command);

        return ResponseEntity.ok(token);
    }
}
