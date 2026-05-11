package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.dto.AuthResponseDto;
import com.akayxn.cryptocurrencymarkettracker.dto.SignInRequestDto;
import com.akayxn.cryptocurrencymarkettracker.dto.SignUpRequestDto;
import com.akayxn.cryptocurrencymarkettracker.exception.ResourceAlreadyExistsException;
import com.akayxn.cryptocurrencymarkettracker.exception.ResourceNotFoundException;
import com.akayxn.cryptocurrencymarkettracker.model.User;
import com.akayxn.cryptocurrencymarkettracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto login(SignInRequestDto signInDetails){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDetails.getEmail(),signInDetails.getPassword())
        );

        var searchedUser = userRepository.findByEmail(signInDetails.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("User with the email " +signInDetails.getEmail() +" not found!"));

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(jwtService.generateToken(searchedUser));

        return authResponseDto;
    }

    public AuthResponseDto register(SignUpRequestDto signUpDetails){

        if(userRepository.existsByUsername(signUpDetails.getUsername()) ||
            userRepository.existsByEmail(signUpDetails.getEmail())
        ) throw new ResourceAlreadyExistsException("Username or Email already exists.");

        signUpDetails.setPassword(passwordEncoder.encode(signUpDetails.getPassword()));

        User user = new User();
        user.setUsername(signUpDetails.getUsername());
        user.setPassword(signUpDetails.getPassword());
        user.setEmail(signUpDetails.getEmail());

        var savedUser= userRepository.save(user);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(jwtService.generateToken(savedUser));

        return authResponseDto;
    }




}
