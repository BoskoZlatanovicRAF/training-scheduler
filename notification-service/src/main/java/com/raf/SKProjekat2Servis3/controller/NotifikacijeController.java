package com.raf.SKProjekat2Servis3.controller;

import com.raf.SKProjekat2Servis3.domain.Notifikacije;
import com.raf.SKProjekat2Servis3.repository.NotifikacijeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotifikacijeController {

    //TODO: security check

    private NotifikacijeRepository notifikacijeRepository;


    @GetMapping("/fingerprint")
    public String getUsername(@RequestParam("fingerprint") String fingerprint) {
        return notifikacijeRepository.findByFingerprint(fingerprint).orElseThrow(() -> new RuntimeException("Not found")).getEmail();
    }

    @GetMapping("/activationFingerprint")
    public String getActivationUsername(@RequestParam("fingerprint") String fingerprint) {

        Notifikacije notifikacija = notifikacijeRepository.findByFingerprint(fingerprint).orElseThrow(() -> new RuntimeException("Not found"));
        if (notifikacija.getTipNotifikacije().equals("REGISTRACIJA")) {
            return notifikacija.getEmail();
        } else {
            throw new RuntimeException("Not found");
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Notifikacije>> getUserNotifications(){
        List<Notifikacije> notifikacije = notifikacijeRepository.findAll();
        return ResponseEntity.ok(notifikacije);
    }
    @GetMapping("currentNotification")
    public ResponseEntity<List<Notifikacije>> getCurrentNotification(@RequestParam("username") String email){
        List<Notifikacije> notifikacije = notifikacijeRepository.listNotificationsAfter24Hours(email);
        return ResponseEntity.ok(notifikacije);
    }
}

