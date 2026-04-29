package com.akayxn.cryptocurrencymarkettracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CryptocurrencyMarketTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyMarketTrackerApplication.class, args);
    }

}
