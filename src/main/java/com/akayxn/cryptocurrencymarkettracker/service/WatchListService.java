package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.model.User;
import com.akayxn.cryptocurrencymarkettracker.model.WatchList;
import com.akayxn.cryptocurrencymarkettracker.repository.UserRepository;
import com.akayxn.cryptocurrencymarkettracker.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    public List<WatchList> getAllWatchListOfUser(User user){
        return watchListRepository.findByUser(user);
    }

    public WatchList addToWatchList(CryptoAsset cryptoAsset){
        // searched for the username in the context, the username is stored in the context after the user logs in
        var username =SecurityContextHolder.getContext().getAuthentication().getName();
//      then we are loading the whole user using the username because our next function needs it.
       var searchedUser = userRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("User with that username found!"));
//      now after getting the username we search for the watchlist if it exists or not;
       var searchedWatchlist = watchListRepository.findByUserAndCryptoAsset(searchedUser,cryptoAsset);

        if(searchedWatchlist != null){
            throw new ResourceNotFoundException("The resource already exists.");
        }

        WatchList watchlist = new WatchList();
        watchlist.setUser(searchedUser);
        watchlist.setCryptoAsset(cryptoAsset);

        return    watchListRepository.save(watchlist);

    }

}
