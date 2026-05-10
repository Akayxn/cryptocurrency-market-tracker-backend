package com.akayxn.cryptocurrencymarkettracker.repository;

import com.akayxn.cryptocurrencymarkettracker.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoAssetRepository extends JpaRepository<CryptoAsset,Long> {

    CryptoAsset findBySymbol(String symbol);

}
