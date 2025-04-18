package com.raf.SKProjekat2Servis3.servis;

import com.raf.SKProjekat2Servis3.domain.Notifikacije;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationCronJob {

    private final NotifikacijeService notifikacijeService;
    private final EmailService emailService;
    @Scheduled(cron = "0 0 * * * *") // Run at the top of every hour
    public void runTask() {
        List<Notifikacije> getReminder = notifikacijeService.getReminder();

        for (Notifikacije notifikacije : getReminder) {
            emailService.sendSimpleMessage(notifikacije.getEmail(), "Reminder", "Reminder for your training starting at" + notifikacije.getDatum());
        }
        notifikacijeService.updateToSent(getReminder);
    }
}
