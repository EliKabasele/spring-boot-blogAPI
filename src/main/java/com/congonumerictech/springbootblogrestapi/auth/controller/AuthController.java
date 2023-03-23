package com.congonumerictech.springbootblogrestapi.auth.controller;

import com.congonumerictech.springbootblogrestapi.auth.Service.AuthService;
import com.congonumerictech.springbootblogrestapi.auth.dto.JWTAuthResponseDto;
import com.congonumerictech.springbootblogrestapi.auth.dto.LoginDto;
import com.congonumerictech.springbootblogrestapi.auth.dto.RegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        String jwtToken = authService.login(loginDto);
        JWTAuthResponseDto jwtAuthResponseDto = new JWTAuthResponseDto();
        jwtAuthResponseDto.setAccessToken(jwtToken);

        return ResponseEntity.ok(jwtAuthResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
