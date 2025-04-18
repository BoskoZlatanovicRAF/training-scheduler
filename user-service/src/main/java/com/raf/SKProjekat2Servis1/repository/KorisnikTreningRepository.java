package com.raf.SKProjekat2Servis1.repository;

import com.raf.SKProjekat2Servis1.domain.KorisnikTrening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface KorisnikTreningRepository extends JpaRepository<KorisnikTrening, Integer> {

    Optional<KorisnikTrening> findByKorisnikIdCard(Integer korisnikIdCard);



}
