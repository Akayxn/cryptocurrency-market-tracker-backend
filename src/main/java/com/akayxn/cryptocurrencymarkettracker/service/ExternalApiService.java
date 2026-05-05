package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
public class ExternalApiService {

    @Value("${coingecko.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create("https://api.coingecko.com");
    public List<CoinGeckoResponseDto> fetchTopCoins(){

        return restClient.get()
                .uri("/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&x_cg_demo_api_key="+apiKey)
                .retrieve()
                // the parametermizedTyperefence is basically used here because in default they
                // don't know how to deserialize it that's why its used in here.
                .body(new ParameterizedTypeReference<List<CoinGeckoResponseDto>>() {});
    }
}
