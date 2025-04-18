package com.raf.SKProjekat2Servis1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "Korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Integer idCard;

    @Column(name = "username", nullable = false, length = 45, unique = true)
    private String username;
    @Column(name = "ime", nullable = false, length = 45)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 45)
    private String prezime;

    @Column(name = "status", length = 45, columnDefinition = "VARCHAR(45) DEFAULT 'OFFLINE'")
    private String status;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;


    @Column(name = "password", length = 256)
    private String password;

    @ManyToOne
    @JoinColumn(name = "tipovi korisnika_id_tip", referencedColumnName = "id_tip", nullable = false)
    private TipKorisnika tipKorisnika;

}
