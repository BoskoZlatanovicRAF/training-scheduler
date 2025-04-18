package com.raf.SKProjekat2Servis1.services;

import io.jsonwebtoken.Claims;

public interface TokenService {

    String generate(Claims claims);

    Claims parse(String token);

}
