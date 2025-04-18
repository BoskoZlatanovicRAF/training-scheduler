package com.raf.SKProjekat2Servis1.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "tokens") // Assuming the table name is 'tokens'
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Auto-incremented ID

    //make a token
    @Column(name = "token")
    private String token;

    @Column(name = "valid_to")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime validTo;

    @Column(name = "username")
    private String username;

    @Column(name = "prezime")
    private String prezime; // Assuming 'prezime' is a string

}