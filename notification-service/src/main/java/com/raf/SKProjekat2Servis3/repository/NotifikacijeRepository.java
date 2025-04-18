package com.raf.SKProjekat2Servis3.repository;

import com.raf.SKProjekat2Servis3.domain.Notifikacije;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotifikacijeRepository extends JpaRepository<Notifikacije, Integer> {

    List<Notifikacije> findByEmail(String email);

    List<Notifikacije> findByTipNotifikacijeAndEmail(String tipNotifikacije, String email);

    List<Notifikacije> findByTipNotifikacijeAndDatumAndEmail(String tipNotifikacije, LocalDateTime datum, String email);

    Optional<Notifikacije> findByFingerprint(String fingerprint);

    @Query(value = "SELECT * FROM Notifikacije n WHERE n.tip_notifikacije = 'Reminder' and n.datum < CURRENT_TIMESTAMP + INTERVAL '24 HOURS' AND n.status = 'WAITING'", nativeQuery = true)
    List<Notifikacije> findRemindersWithinNext24Hours();

    @Query(value = "SELECT * FROM Notifikacije n WHERE n.status = 'SENT' and n.email = :email", nativeQuery = true)
    List<Notifikacije> listNotificationsAfter24Hours(String email);

    Optional<Notifikacije> findByEmailAndTipNotifikacijeAndDatum(String email, String tipNotifikacije, LocalDateTime datum);
}
