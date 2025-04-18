package com.raf.SKProjekat2Servis1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class KorisnikDto {

    private Integer idCard;
    private String username;
    private String ime;
    private String prezime;
    private String status;
    private LocalDate datum;
    private String tipKorisnika;
    private Integer salaId;

}
