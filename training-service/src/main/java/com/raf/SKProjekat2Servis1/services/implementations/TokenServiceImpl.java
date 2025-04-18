package com.raf.SKProjekat2Servis1.services.implementations;

import com.raf.SKProjekat2Servis1.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    public String generate(Claims claims) {


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret).
                parseClaimsJws(token).getBody();
    }
}
