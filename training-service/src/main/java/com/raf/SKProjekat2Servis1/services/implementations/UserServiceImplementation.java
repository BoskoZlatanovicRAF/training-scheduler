package com.raf.SKProjekat2Servis1.services.implementations;

import com.raf.SKProjekat2Servis1.domain.Korisnik;
import com.raf.SKProjekat2Servis1.domain.KorisnikTrening;
import com.raf.SKProjekat2Servis1.domain.MenadzerSala;
import com.raf.SKProjekat2Servis1.dto.KorisnikCreateDto;
import com.raf.SKProjekat2Servis1.dto.KorisnikDto;
import com.raf.SKProjekat2Servis1.dto.TokenRequestDto;
import com.raf.SKProjekat2Servis1.dto.TokenResponseDto;
import com.raf.SKProjekat2Servis1.mapper.KorisnikMapper;
import com.raf.SKProjekat2Servis1.repository.KorisnikRepository;
import com.raf.SKProjekat2Servis1.repository.KorisnikTreningRepository;
import com.raf.SKProjekat2Servis1.repository.MenadzerSalaRepository;
import com.raf.SKProjekat2Servis1.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final KorisnikMapper korisnikMapper;
    private final KorisnikRepository korisnikRepository;
    private final TokenServiceImpl tokenService;

    private final KorisnikTreningRepository korisnikTreningRepository;

    private final MenadzerSalaRepository menadzerSalaRepository;

    @Autowired
    private final RestTemplate notificationServiceRestTemplate;



    @Override
    public Korisnik findUser(String username) {
        return korisnikRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String
                .format("User with username: %s not found.", username)));
    }

    @Override
    public KorisnikDto findUserByIdCard(Integer idCard) {
        Korisnik korisnik = korisnikRepository.findByIdCard(idCard).orElseThrow(() -> new NotFoundException(String
                .format("User with idCard: %s not found.", idCard)));
        return korisnikMapper.toDto(korisnik);

    }

    public Integer getManagerbySala(Integer salaId){
        MenadzerSala manager = menadzerSalaRepository.findBySala(salaId).orElseThrow(() -> new NotFoundException(String
                .format("Manager with salaId: %s not found.", salaId)));
        return manager.getKorisnikIdCard();
    }
    @Override
    public void save(Korisnik user) {
        korisnikRepository.save(user);
    }

    @Override
    public KorisnikDto register(KorisnikCreateDto dto, String tip) {
        dto.setTipKorisnika(tip);
        Korisnik newKorisnik = korisnikMapper.toEntity(dto);
        try {
            korisnikRepository.save(newKorisnik);

            if (tip.equals("Standard")) {
                KorisnikTrening korisnikTrening = new KorisnikTrening();
                korisnikTrening.setKorisnikIdCard(newKorisnik.getIdCard());
                korisnikTrening.setBrojTreninga(0);
                korisnikTreningRepository.save(korisnikTrening);
            }
            else{
                MenadzerSala manager = new MenadzerSala();
                manager.setKorisnikIdCard(newKorisnik.getIdCard());
                manager.setSala(dto.getSalaId());
                menadzerSalaRepository.save(manager);
            }

            return korisnikMapper.toDto(newKorisnik);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void activate(String footprint) {
        try {
            String url =  UriComponentsBuilder.fromUriString("/notification/fingerprint")
                    .queryParam("fingerprint", footprint)
                    .toUriString();
            String username = notificationServiceRestTemplate.getForObject(url, String.class);
            Korisnik user = korisnikRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String
                    .format("User with username: %s not found.", username)));
            user.setStatus("ONLINE");
            korisnikRepository.save(user);
        }
        catch (Exception e){
            throw new NotFoundException(String.format("User with username: %s not found.", footprint));
        }
    }

    @Override
    public boolean createManagerSala(Integer idCard, Integer salaId) {
        MenadzerSala manager = new MenadzerSala();
        manager.setKorisnikIdCard(idCard);
        manager.setSala(salaId);
        try {
            menadzerSalaRepository.save(manager);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    @Override
    public List<KorisnikDto> allUsers() {
        List<Korisnik> korisniks = korisnikRepository.findAll();
        List<KorisnikDto> dtos = new ArrayList<>();

        for (Korisnik korisnik : korisniks) {
            dtos.add(korisnikMapper.toDto(korisnik));
        }
        return dtos;
    }

    @Override
    public KorisnikDto updateUser(KorisnikDto dto) {
        Korisnik korisnik = korisnikRepository.findByIdCard(dto.getIdCard()).orElseThrow(() -> new NotFoundException(String
                .format("User with idCard: %s not found.", dto.getIdCard())));
        korisnik.setIme(dto.getIme());
        korisnik.setPrezime(dto.getPrezime());
        korisnik.setDatum(dto.getDatum());
        korisnik.setUsername(dto.getUsername());
        korisnikRepository.save(korisnik);
        if (dto.getSalaId() != null) {
            Optional<MenadzerSala> manager = menadzerSalaRepository.findByKorisnikIdCard(dto.getIdCard());
            if (manager.get().getSala() != dto.getSalaId()){
                manager.get().setSala(dto.getSalaId());
                menadzerSalaRepository.save(manager.get());
            }

        }

        return korisnikMapper.toDto(korisnik);
    }

    @Override
    public void deactivate(String username) {
        Korisnik user = korisnikRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String
                .format("User with username: %s not found.", username)));
        user.setStatus("OFFLINE");
        korisnikRepository.save(user);

    }

    @Override
    public void activateAdmin(String username) {
        Korisnik user = korisnikRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String
                .format("User with username: %s not found.", username)));
        user.setStatus("ONLINE");
        korisnikRepository.save(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto dto) throws NotFoundException{

        //Find user
        Korisnik user = korisnikRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", dto.getUsername(),
                                dto.getPassword())));

        if (user.getStatus() == null) {
            throw new NotFoundException(String.format("User with username: %s is not activated.", dto.getUsername()));
        }
        if (!user.getStatus().equals("ONLINE")) {
            throw new NotFoundException(String.format("User with username: %s is not activated.", dto.getUsername()));
        }
        //Create token payload
        Claims claims = (Claims) Jwts.claims();
        claims.put("id", user.getIdCard());
        claims.put("role", user.getTipKorisnika().getTip());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));

    }

    @Override
    public Integer findMenagerSalaByIdCard(Integer idCard) {
        MenadzerSala manager = menadzerSalaRepository.getReferenceById(idCard);
        return manager.getSala();
    }
    public KorisnikDto getKorisnikFromToken(String token){
        Claims claims = tokenService.parse(token);
        Korisnik korisnik = korisnikRepository.findByIdCard((Integer) claims.get("id")).orElseThrow(() -> new NotFoundException(String
                .format("User with id: %s not found.", claims.get("id").toString())));


        return korisnikMapper.toDto(korisnik);
    }

}
