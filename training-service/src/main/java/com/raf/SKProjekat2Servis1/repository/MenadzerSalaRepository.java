package com.raf.SKProjekat2Servis1.repository;

import com.raf.SKProjekat2Servis1.domain.MenadzerSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MenadzerSalaRepository extends JpaRepository<MenadzerSala, Integer> {

    Optional<MenadzerSala> findByKorisnikIdCard(Integer korisnikIdCard);

    Optional<MenadzerSala> findBySala(Integer sala);

}
