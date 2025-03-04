package com.daehan.back.user.ui;

import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.application.service.UserService;
import com.daehan.back.user.ui.dto.req.UserCreateRequest;
import com.daehan.back.user.ui.dto.res.UserResponse;
import com.daehan.back.user.ui.mapper.UserCommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserCommandMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid final UserCreateRequest request
    ) {
        UserCreateCommand command = userMapper.toCommand(request);
        Long userId = userService.createUser(command);

        return ResponseEntity.created(URI.create("/user/"+userId)).body(new UserResponse(userId));
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
