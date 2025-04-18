package com.raf.SKProjekat2Servis1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class KorisnikCreateDto {

    private String username;
    private String ime;
    private String prezime;
    private String status;
    private LocalDate datum;
    private String tipKorisnika;
    private String password;
    private int salaId;
}
