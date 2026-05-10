package com.akayxn.cryptocurrencymarkettracker.kafka;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
import com.akayxn.cryptocurrencymarkettracker.exception.ResourceNotFoundException;
import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.repository.CryptoAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CryptoPriceConsumer {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisTemplate<String,Object> redisTemplate;
    private final CryptoAssetRepository cryptoAssetRepository;
//  the kafkaListener is basically used to listen for the kafka which is sending the message and
//    for me now its listening to crypto-prices

    @KafkaListener(topics = "crypto-prices",groupId = "crypto-prices")
    private void consumePriceList(CoinGeckoResponseDto coin){
        //  the opsForValue is used for simple key and value pairs .
        redisTemplate.opsForValue().set(coin.getSymbol(),coin);


        var cryptoAsset = cryptoAssetRepository.findBySymbol(coin.getSymbol());

        if(cryptoAsset == null){
            CryptoAsset asset = new CryptoAsset();
            asset.setName(coin.getName());
            asset.setCurrentPrice(coin.getCurrentPrice());
            asset.setSymbol(coin.getSymbol());
            asset.setPercentageChange(coin.getPriceChangePercentage());
            cryptoAssetRepository.save(asset);
        }
        else {
            cryptoAsset.setCurrentPrice(coin.getCurrentPrice());
            cryptoAsset.setPercentageChange(coin.getPriceChangePercentage());
            cryptoAssetRepository.save(cryptoAsset);
        }
        simpMessagingTemplate.convertAndSend("/topic/crypto-prices",coin);

    }


}
