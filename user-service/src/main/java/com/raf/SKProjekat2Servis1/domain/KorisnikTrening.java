package com.raf.SKProjekat2Servis1.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "korisnik_trening")
public class KorisnikTrening {

    @Id
    @Column(name = "Korisnik_id_card")
    private Integer korisnikIdCard;

    @Column(name = "broj_treninga", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer brojTreninga;

    @ManyToOne
    @JoinColumn(name = "Korisnik_id_card", referencedColumnName = "id_card", insertable = false, updatable = false)
    private Korisnik korisnik;

    // Standard setters and other methods
}
