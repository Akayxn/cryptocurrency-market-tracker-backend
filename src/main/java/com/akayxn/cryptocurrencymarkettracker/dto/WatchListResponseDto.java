package com.akayxn.cryptocurrencymarkettracker.dto;

import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchListResponseDto {
    private Long watchListId;

    private String username;

    private CryptoAsset cryptoAsset;
}
