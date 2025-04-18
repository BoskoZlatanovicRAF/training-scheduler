package com.raf.SKProjekat2Servis1.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tip_korisnik")
public class TipKorisnika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tip")
    private Integer id;

    @Column(name = "tip", nullable = false, length = 45)
    private String tip;

}
