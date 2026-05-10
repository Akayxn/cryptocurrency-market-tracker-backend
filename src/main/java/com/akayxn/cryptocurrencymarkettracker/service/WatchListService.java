package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.dto.WatchListResponseDto;
import com.akayxn.cryptocurrencymarkettracker.exception.ResourceAlreadyExistsException;
import com.akayxn.cryptocurrencymarkettracker.exception.ResourceNotFoundException;
import com.akayxn.cryptocurrencymarkettracker.model.WatchList;
import com.akayxn.cryptocurrencymarkettracker.repository.CryptoAssetRepository;
import com.akayxn.cryptocurrencymarkettracker.repository.UserRepository;
import com.akayxn.cryptocurrencymarkettracker.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final CryptoAssetRepository assetRepository;

    public List<WatchListResponseDto> getAllWatchListOfUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var loadedUser = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User with that username not found!"));
        var watchLists = watchListRepository.findByUser(loadedUser);

        return watchLists.stream().map(this::convertToDto).toList();
    }

    public WatchListResponseDto addToWatchList(Long cryptoId){
        // searched for the username in the context, the username is stored in the context after the user logs in
        var username =SecurityContextHolder.getContext().getAuthentication().getName();
//      then we are loading the whole user using the username because our next function needs it.
       var searchedUser = userRepository.findByUsername(username)
               .orElseThrow(() -> new ResourceNotFoundException("User with that username found!"));
//      now after getting the username we search for the watchlist if it exists or not;
        var cryptoAsset= assetRepository.findById(cryptoId).orElseThrow(()-> new ResourceNotFoundException("Crypto Asset with that id not found!"));

       var searchedWatchlist = watchListRepository.findByUserAndCryptoAsset(searchedUser,cryptoAsset);

        if(searchedWatchlist != null){
            throw new ResourceAlreadyExistsException("The resource already exists.");
        }

        WatchList watchlist = new WatchList();
        watchlist.setUser(searchedUser);
        watchlist.setCryptoAsset(cryptoAsset);

        var savedWatchList= watchListRepository.save(watchlist);


        return convertToDto(savedWatchList);
    }


    public void removeFromWatchList(Long cryptoAssetId){
        // firstly we get the username from the security context
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

//        then we find the user using the username we got from the security context.
        var loadedUser = userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User with that username not found!"));

        var searchedCryptoAsset = assetRepository.findById(cryptoAssetId)
                .orElseThrow(()-> new ResourceNotFoundException("Crypto Asset with that id not found!"));
        // then we search from the specific watchlist if the user has it or not using the both the user we loaded and the crypto asset
        var searchedWatchlist =  watchListRepository.findByUserAndCryptoAsset(loadedUser,searchedCryptoAsset);

        if(searchedWatchlist==null) throw new ResourceNotFoundException("No cryptoAsset with that name found!");

        watchListRepository.delete(searchedWatchlist);

    }

    private WatchListResponseDto convertToDto(WatchList watchList){
        WatchListResponseDto watchListResponseDto = new WatchListResponseDto();
        watchListResponseDto.setWatchListId(watchList.getWatchlistId());
        watchListResponseDto.setUsername(watchList.getUser().getUsername());
        watchListResponseDto.setCryptoAsset(watchList.getCryptoAsset());
        return watchListResponseDto;
    }
}
