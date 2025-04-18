package com.raf.SKProjekat2Servis1.repository;

import com.raf.SKProjekat2Servis1.domain.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Integer>{
    Optional<TipKorisnika> findById(Integer id);
    Optional<TipKorisnika> findByTip(String naziv);
}
