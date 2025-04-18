package com.raf.SKProjekat2Servis1.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menadzer_sala")
public class MenadzerSala {


    @Column(name = "sala", length = 256)
    private Integer sala;

    @Id
    @Column(name = "Korisnik_id_card", nullable = false)
    private Integer korisnikIdCard;

    @ManyToOne
    @JoinColumn(name = "Korisnik_id_card", referencedColumnName = "id_card", insertable = false, updatable = false)
    private Korisnik korisnik;

}



