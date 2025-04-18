package com.raf.SKProjekat2Servis1.mapper;

import com.raf.SKProjekat2Servis1.domain.KorisnikTrening;
import com.raf.SKProjekat2Servis1.dto.KorisnikTreningDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KorisnikTreningMapper {

    public KorisnikTreningDto toDto(KorisnikTrening entity){

        KorisnikTreningDto dto = new KorisnikTreningDto();

        dto.setUsername(entity.getKorisnik().getUsername());
        dto.setIdCard(entity.getKorisnikIdCard());
        dto.setBrojTreninga(dto.getBrojTreninga());

        return dto;
    }
}
