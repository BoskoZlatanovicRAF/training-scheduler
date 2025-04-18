package com.raf.SKProjekat2Servis3.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KorisnikResetQueueDto {

    private String username;
    private int id;
    private String tipKorisnika;
    private String url = UUID.randomUUID().toString();
    @Override
    public String toString() {

        return "Pozdrav " +
                username + " " +  '\'' +
                id + '\'' +
                ", token='localhost:8080/reset?token=" + url + '\''; // replace localhost:8080 with your domain name
    }
}
