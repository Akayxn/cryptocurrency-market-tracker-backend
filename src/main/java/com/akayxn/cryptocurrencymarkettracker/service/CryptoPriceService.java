package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.repository.CryptoAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CryptoPriceService {

    private final RedisTemplate<String,Object> redisTemplate;

    public List<CoinGeckoResponseDto> getAllPrices(){
        List<CoinGeckoResponseDto> assetList = new ArrayList<>();
       var keysList =redisTemplate.keys("*") ;

       for(String key: keysList){
           var value = redisTemplate.opsForValue().get(key);
           assetList.add((CoinGeckoResponseDto)value);
       }
       return assetList;
    }


}
