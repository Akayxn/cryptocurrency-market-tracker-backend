package com.akayxn.cryptocurrencymarkettracker.controller;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
import com.akayxn.cryptocurrencymarkettracker.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/prices")
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @GetMapping
    public ResponseEntity<List<CoinGeckoResponseDto>> getAllPrices(){
        return new ResponseEntity<>(cryptoPriceService.getAllPrices(),HttpStatus.OK);
    }

}
