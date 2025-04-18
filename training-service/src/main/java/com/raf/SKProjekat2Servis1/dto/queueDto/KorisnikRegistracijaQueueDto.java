package com.raf.SKProjekat2Servis1.dto.queueDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KorisnikRegistracijaQueueDto {

    private String email;
    private String prezime;
    private String ime;
    private String token;
    private String tipKorisnika;
}
