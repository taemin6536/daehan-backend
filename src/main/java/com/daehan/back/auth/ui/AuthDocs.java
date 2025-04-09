package com.daehan.back.auth.ui;

import com.daehan.back.auth.ui.dto.req.LoginRequest;
import com.daehan.back.user.ui.dto.res.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface AuthDocs {

    @Operation(summary = "회원 로그인", description = "회원 로그인 기능", tags = {"Auth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "로그인 불가능")
    })
    ResponseEntity<String> login(
            @Parameter(description = "이메일, 비밀번호", required = true)
            LoginRequest request
    );


    @Operation(summary = "Test", description = "Test", tags = {"Auth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class ))),
            @ApiResponse(responseCode = "404", description = "Test")
    })
    String test();
}
