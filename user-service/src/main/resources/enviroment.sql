
CREATE TABLE tip_korisnik (
                              id_tip INT PRIMARY KEY AUTO_INCREMENT,
                              tip VARCHAR(45) NOT NULL
);


CREATE TABLE Korisnik (
                          id_card INT PRIMARY KEY AUTO_INCREMENT,
                          username VARCHAR(45) NOT NULL UNIQUE,
                          ime VARCHAR(45) NOT NULL,
                          prezime VARCHAR(45) NOT NULL,
                          status VARCHAR(45) NOT NULL DEFAULT 'ONLINE',
                          datum DATETIME NOT NULL,
                          password VARCHAR(256),
                          tipovi_korisnika_id_tip INT NOT NULL,
                          FOREIGN KEY (tipovi_korisnika_id_tip) REFERENCES tip_korisnik(id_tip)
);

CREATE TABLE menadzer_sala (
                               sala VARCHAR(256) PRIMARY KEY,
                               Korisnik_id_card INT NOT NULL,
                               FOREIGN KEY (Korisnik_id_card) REFERENCES Korisnik(id_card)
);
CREATE TABLE korisnik_trening (
                                  Korisnik_id_card INT PRIMARY KEY,
                                  broj_treninga INT NOT NULL DEFAULT 0,
                                  FOREIGN KEY (Korisnik_id_card) REFERENCES Korisnik(id_card)
);
