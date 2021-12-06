package com.enojen.getir.controller;

import com.enojen.getir.payload.request.LoginRequest;
import com.enojen.getir.payload.request.SignupRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.payload.response.JwtResponse;
import com.enojen.getir.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @Operation(summary = "Sign in app", description = "Sign in app")
    public BaseResponse<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.signIn(loginRequest);

    }

    @PostMapping("/signup")
    @Operation(summary = "sign up app", description = "sign up app")
    public BaseResponse<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);

    }
}