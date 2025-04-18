package com.raf.SKProjekat2Servis3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KorisnikRegistracijaQueueDto {

    private String ime;

    private String prezime;

    private String token;

    private String email;

    private String tipKorisnika;

    @Override
    public String toString() {
        return "Pozdrav " +
                ime + " " +  '\'' +
                 prezime + '\'' +
                ", token='localhost:8080/activate?token=" + token + '\''; // replace localhost:8080 with your domain name
    }
}
