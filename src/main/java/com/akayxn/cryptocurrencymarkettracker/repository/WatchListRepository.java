package com.akayxn.cryptocurrencymarkettracker.repository;

import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import com.akayxn.cryptocurrencymarkettracker.model.User;
import com.akayxn.cryptocurrencymarkettracker.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList,Long> {

    List<WatchList> findByUser(User user);




}
