package com.akayxn.cryptocurrencymarkettracker.kafka;

import com.akayxn.cryptocurrencymarkettracker.dto.CoinGeckoResponseDto;
import com.akayxn.cryptocurrencymarkettracker.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CryptoPriceProducer {

   private final ExternalApiService externalApiService;
   private final KafkaTemplate<String,CoinGeckoResponseDto> kafkaTemplate;

   // basically this function runs every 30 second 30000 == 30 seconds
   @Scheduled(fixedDelay = 30000)
    private void fetchTopCoins(){
       try{
           System.out.println("Scheduler Fired!");
           // we fetch the List of CryptoCoins from the externalApi
           var coinList = externalApiService.fetchTopCoins();
           // then we iterate over list and send each coin over to them.
           coinList.forEach(coin -> kafkaTemplate.send("crypto-prices",coin));
       }
       catch (Exception e){
           System.out.println("Scheduler error: " + e.getMessage());
       }

   }

}
