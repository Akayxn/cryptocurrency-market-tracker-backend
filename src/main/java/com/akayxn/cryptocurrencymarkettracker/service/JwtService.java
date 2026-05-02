package com.akayxn.cryptocurrencymarkettracker.service;

import com.akayxn.cryptocurrencymarkettracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(User user){
        // uses string builder method to only get specific functions
        return Jwts.builder()
                // sets the payload for the token
                .subject(user.getUsername())
                // setting up the iat
                .issuedAt(new Date())
                // setting the expiration which is 1 day
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                // after that signing the token with our signing key.
                .signWith(getSigningKey())
                .compact(); // then we build it
    }

    public String getUsernameByToken(String token){
                 // jwt parser basically are used to read JWTS
        return Jwts.parser()
                // we let it know to check it with our signing key.
                .verifyWith(getSigningKey())
                // we build the parser
                .build()
                // here it checks and validates the token. It checks the signature and expiration.
                .parseSignedClaims(token)
                // gets the payload which contains all our claims like subject,IAT,expiration and custom claims.
                .getPayload()
                // getSubject contains our username,so we return the username here
                .getSubject();

    }

    public boolean isValidToken(User user, String token){
        var payload= Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();


//         this checks if the payload username and the user model is equal
//        then after that it also checks if the token is expired or not
//        the before method in the new Date() basically checks whether
//        our current time is before the expiration or after the expiration and returns boolean
        return user.getUsername().equals(payload.getSubject())
                && new Date().before(payload.getExpiration());
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes=  Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }





}
