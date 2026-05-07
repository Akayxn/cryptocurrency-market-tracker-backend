package com.akayxn.cryptocurrencymarkettracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchlist_id")
    private Long watchlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private CryptoAsset cryptoAsset;

}
