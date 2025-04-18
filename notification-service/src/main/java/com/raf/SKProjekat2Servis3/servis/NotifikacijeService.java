package com.raf.SKProjekat2Servis3.servis;

import com.raf.SKProjekat2Servis3.domain.Notifikacije;
import com.raf.SKProjekat2Servis3.repository.NotifikacijeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class NotifikacijeService {

    private NotifikacijeRepository notifikacijeRepository;
    private RestTemplate userServiceRestTemplate;

    public boolean createNotifikacija(String email, String tipNotifikacije, String tipKorisnika, LocalDateTime datum, String fingerprint, String status) {
        Notifikacije notifikacija = new Notifikacije();
        notifikacija.setEmail(email);
        notifikacija.setTipNotifikacije(tipNotifikacije);
        notifikacija.setTipKorisnika(tipKorisnika);
        notifikacija.setDatum(datum);
        notifikacija.setFingerprint(fingerprint);
        notifikacija.setStatus(status);
        notifikacijeRepository.save(notifikacija);
        return true;
    }

    public String getUsername(Integer id) {
        return userServiceRestTemplate.getForObject("/user/getKorisnikByIdCard?idCard="+id, String.class);
    }

    List<Notifikacije> getReminder() {
        return notifikacijeRepository.findRemindersWithinNext24Hours();

    }

    List<Notifikacije> getAllNotifications() {
        return notifikacijeRepository.findAll();
    }

    List<Notifikacije> getUserNotifications(String username) {
        return notifikacijeRepository.listNotificationsAfter24Hours(username);
    }

    public void updateToSent(List<Notifikacije> notifikacijes){
        for (Notifikacije notifikacije : notifikacijes) {
            notifikacije.setStatus("SENT");
            notifikacijeRepository.save(notifikacije);
        }
    }

    public void deleteNotification(String username,LocalDateTime time){
        Optional<Notifikacije> notifikacije = notifikacijeRepository.findByEmailAndTipNotifikacijeAndDatum(username,"Reminder",time);
        notifikacije.ifPresent(notifikacijeRepository::delete);
    }


}
