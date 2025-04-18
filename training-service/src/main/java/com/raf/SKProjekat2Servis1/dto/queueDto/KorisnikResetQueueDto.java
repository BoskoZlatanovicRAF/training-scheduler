package com.raf.SKProjekat2Servis1.dto.queueDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KorisnikResetQueueDto {

    private String username;
    private int id;
    private String tipKorisnika;
    private String url;


}
