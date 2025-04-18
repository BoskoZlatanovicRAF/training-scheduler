package com.raf.SKProjekat2Servis1.repository;

import com.raf.SKProjekat2Servis1.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{

    Optional<Korisnik> findByUsername(String username);

    Optional<Korisnik> findByIdCard(Integer card);

    Optional<Korisnik> findByUsernameAndPassword(String username, String password);


}
