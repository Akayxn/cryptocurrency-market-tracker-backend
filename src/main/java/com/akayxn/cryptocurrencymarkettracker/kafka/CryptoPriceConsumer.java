package com.akayxn.cryptocurrencymarkettracker.kafka;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
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
//  the kafkaListener is basically used to listen for the kafka which is sending the message and
//    for me now its listening to crypto-prices

    @KafkaListener(topics = "crypto-prices",groupId = "crypto-prices")
    private void consumePriceList(CoinGeckoResponseDto coin){
        //  the opsForValue is used for simple key and value pairs .
        redisTemplate.opsForValue().set(coin.getSymbol(),coin);
        simpMessagingTemplate.convertAndSend("/topic/crypto-prices",coin);

    }


}
