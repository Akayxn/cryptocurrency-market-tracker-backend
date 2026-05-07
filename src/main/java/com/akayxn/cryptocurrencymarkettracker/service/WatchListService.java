package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.model.User;
import com.akayxn.cryptocurrencymarkettracker.model.WatchList;
import com.akayxn.cryptocurrencymarkettracker.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;

    public List<WatchList> getAllWatchListOfUser(User user){
        return watchListRepository.findByUser(user);
    }

    public WatchList addToWatchList(User user, CryptoAsset cryptoAsset){
        var searchedWatchList = watchListRepository.findByUser(user);


    }

}
