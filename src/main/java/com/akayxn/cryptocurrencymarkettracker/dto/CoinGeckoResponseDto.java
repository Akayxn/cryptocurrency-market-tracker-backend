package com.akayxn.cryptocurrencymarkettracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinGeckoResponseDto {

    private String id;

    private String name;

    private String symbol;

    @JsonProperty("current_price")
    private double currentPrice;

    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage;
}
