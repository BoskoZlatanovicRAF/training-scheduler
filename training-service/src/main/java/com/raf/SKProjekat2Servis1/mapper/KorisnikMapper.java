package com.raf.SKProjekat2Servis1.mapper;


import com.raf.SKProjekat2Servis1.domain.Korisnik;
import com.raf.SKProjekat2Servis1.domain.TipKorisnika;
import com.raf.SKProjekat2Servis1.dto.KorisnikCreateDto;
import com.raf.SKProjekat2Servis1.dto.KorisnikDto;
import com.raf.SKProjekat2Servis1.repository.MenadzerSalaRepository;
import com.raf.SKProjekat2Servis1.repository.TipKorisnikaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class KorisnikMapper {
    private final TipKorisnikaRepository tipKorisnikaRepository;
    private final MenadzerSalaRepository menadzerSalaRepository;
    public KorisnikDto toDto(Korisnik entity) {
        KorisnikDto dto = new KorisnikDto();
        dto.setIdCard(entity.getIdCard());
        dto.setUsername(entity.getUsername());
        dto.setIme(entity.getIme());
        dto.setPrezime(entity.getPrezime());
        dto.setStatus(entity.getStatus());
        dto.setDatum(entity.getDatum());

        // Assuming the TipKorisnika entity has a method like getName() to get a string representation
        if (entity.getTipKorisnika() != null) {
            dto.setTipKorisnika(entity.getTipKorisnika().getTip());
        }
        if (entity.getTipKorisnika().getTip().equals("Manager")) {
            dto.setSalaId(Objects.requireNonNull(menadzerSalaRepository.findByKorisnikIdCard(entity.getIdCard()).orElse(null)).getSala());
        }

        return dto;
    }

    public KorisnikCreateDto toCreateDto(Korisnik entity) {
        KorisnikCreateDto dto = new KorisnikCreateDto();
        dto.setUsername(entity.getUsername());
        dto.setIme(entity.getIme());
        dto.setPrezime(entity.getPrezime());
        dto.setStatus(entity.getStatus());
        dto.setDatum(entity.getDatum());
        dto.setPassword(entity.getPassword());
        // Assuming the TipKorisnika entity has a method like getName() to get a string representation
        if (entity.getTipKorisnika() != null) {
            dto.setTipKorisnika(entity.getTipKorisnika().getTip());
        }
        return dto;
    }

    public Korisnik toEntity(KorisnikCreateDto dto) {
        Korisnik entity = new Korisnik();
        entity.setUsername(dto.getUsername());
        entity.setIme(dto.getIme());
        entity.setPrezime(dto.getPrezime());
        entity.setStatus(dto.getStatus());
        entity.setDatum(dto.getDatum());
        entity.setPassword(dto.getPassword());
        entity.setStatus("OFFLINE");
        // Assuming the TipKorisnika entity has a method like getName() to get a string representation
        if (dto.getTipKorisnika() != null) {
            TipKorisnika tipKorisnika = tipKorisnikaRepository.findByTip(dto.getTipKorisnika()).orElse(null);
            entity.setTipKorisnika(tipKorisnika);
        }
        return entity;
    }


}