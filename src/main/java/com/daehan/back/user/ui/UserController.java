package com.daehan.back.user.ui;

import com.daehan.back.user.application.dto.UserCreateCommand;
import com.daehan.back.user.application.service.UserService;
import com.daehan.back.user.ui.dto.req.UserCreateRequest;
import com.daehan.back.user.ui.dto.res.UserResponse;
import com.daehan.back.user.ui.mapper.UserCommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserCommandMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid final UserCreateRequest request
    ) {
        UserCreateCommand command = userMapper.toCommand(request);
        Long userSeq = userService.createUser(command);

        return ResponseEntity.created(URI.create("/user/"+userSeq)).body(new UserResponse(userSeq));
    }

    @DeleteMapping("/delete/{seq}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("seq") final Long seq
    ){
        userService.deleteUser(seq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
