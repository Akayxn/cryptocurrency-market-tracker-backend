package com.akayxn.cryptocurrencymarkettracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignInRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8,max=12)
    private String password;

}