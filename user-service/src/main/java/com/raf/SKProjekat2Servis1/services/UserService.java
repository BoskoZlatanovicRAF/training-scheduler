package com.raf.SKProjekat2Servis1.services;

import com.raf.SKProjekat2Servis1.domain.Korisnik;
import com.raf.SKProjekat2Servis1.dto.KorisnikCreateDto;
import com.raf.SKProjekat2Servis1.dto.KorisnikDto;
import com.raf.SKProjekat2Servis1.dto.TokenRequestDto;
import com.raf.SKProjekat2Servis1.dto.TokenResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    Korisnik findUser(String username);

    KorisnikDto findUserByIdCard(Integer idCard);

    TokenResponseDto login(TokenRequestDto dto);

    Integer findMenagerSalaByIdCard(Integer idCard);

    void save(Korisnik user);
    KorisnikDto register(KorisnikCreateDto dto, String tip);
    void activate(String footprint);

    boolean createManagerSala(Integer idCard, Integer salaId);
    void deactivate(String username);

    void activateAdmin(String username);

    List<KorisnikDto> allUsers();

    KorisnikDto updateUser(KorisnikDto dto);

}


