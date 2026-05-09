package com.akayxn.cryptocurrencymarkettracker.controller;

import com.akayxn.cryptocurrencymarkettracker.dto.ApiResponse;
import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.model.WatchList;
import com.akayxn.cryptocurrencymarkettracker.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchListService watchListService;

    @GetMapping
    public ResponseEntity<List<WatchList>> getAllWatchList(){
        return new ResponseEntity<>(watchListService.getAllWatchListOfUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WatchList> addWatchList(@RequestBody CryptoAsset cryptoAsset){
        var savedWatchlist = watchListService.addToWatchList(cryptoAsset);

        return new ResponseEntity<>(savedWatchlist,HttpStatus.OK);
    }

    @DeleteMapping("/{cryptoId}")
    public ResponseEntity<ApiResponse> deleteWatchList(@PathVariable Long cryptoId){
        watchListService.removeFromWatchList(cryptoId);
        return new ResponseEntity<>(new ApiResponse("Watchlist Deleted Successfully",true),HttpStatus.OK);
    }



}
