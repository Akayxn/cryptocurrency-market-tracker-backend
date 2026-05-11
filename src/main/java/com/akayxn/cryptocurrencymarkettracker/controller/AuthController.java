package com.akayxn.cryptocurrencymarkettracker.controller;

import com.akayxn.cryptocurrencymarkettracker.dto.AuthResponseDto;
import com.akayxn.cryptocurrencymarkettracker.dto.SignInRequestDto;
import com.akayxn.cryptocurrencymarkettracker.dto.SignUpRequestDto;
import com.akayxn.cryptocurrencymarkettracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody SignInRequestDto signInRequestDto){
        return new ResponseEntity<>(authService.login(signInRequestDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody  SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(authService.register(signUpRequestDto),HttpStatus.CREATED);
    }


}
