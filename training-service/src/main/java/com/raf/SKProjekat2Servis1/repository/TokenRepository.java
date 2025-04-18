package com.raf.SKProjekat2Servis1.repository;

import com.raf.SKProjekat2Servis1.domain.MenadzerSala;
import com.raf.SKProjekat2Servis1.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
