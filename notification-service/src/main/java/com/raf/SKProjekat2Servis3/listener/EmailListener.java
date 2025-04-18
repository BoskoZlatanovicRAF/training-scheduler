package com.raf.SKProjekat2Servis3.listener;

import com.raf.SKProjekat2Servis3.dto.CancleTrening;
import com.raf.SKProjekat2Servis3.dto.JoinTreningRegistration;
import com.raf.SKProjekat2Servis3.dto.KorisnikRegistracijaQueueDto;
import com.raf.SKProjekat2Servis3.dto.KorisnikResetQueueDto;
import com.raf.SKProjekat2Servis3.listener.helper.MessageHelper;
import com.raf.SKProjekat2Servis3.repository.NotifikacijeRepository;
import com.raf.SKProjekat2Servis3.servis.EmailService;
import com.raf.SKProjekat2Servis3.servis.NotifikacijeService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EmailListener {
    private MessageHelper messageHelper;
    private EmailService emailService;
    @Autowired
    private JmsTemplate jmsTemplate;
    private NotifikacijeService notifikacijeService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService, NotifikacijeService notifikacijeService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
        this.notifikacijeService = notifikacijeService;
    }

    @JmsListener(destination = "${destination.sendEmails}", concurrency = "5-10")
    public void register(Message message) throws JMSException {
        KorisnikRegistracijaQueueDto userWithToken = messageHelper.getMessage(message, KorisnikRegistracijaQueueDto.class);
        emailService.sendSimpleMessage(userWithToken.getEmail(), "Aktivacioni mail", userWithToken.toString());
        notifikacijeService.createNotifikacija(userWithToken.getEmail(), "Registracija", userWithToken.getTipKorisnika(), LocalDateTime.now(), userWithToken.getToken(),"SENT");
    }

    @JmsListener(destination = "${destination.passwordResetEmails}", concurrency = "5-10")
    public void passwordReset(Message message) throws JMSException {
        KorisnikResetQueueDto userWithToken = messageHelper.getMessage(message, KorisnikResetQueueDto.class);
        emailService.sendSimpleMessage(userWithToken.getUsername(), "Reset mail", userWithToken.toString());
        notifikacijeService.createNotifikacija(userWithToken.getUsername(), "Password Reset", userWithToken.getTipKorisnika(), LocalDateTime.now(), userWithToken.getUrl(),"SENT");
    }


    @JmsListener(destination = "joinTrening", concurrency = "5-10")
    public void joinTrening(Message message) throws JMSException {

        JoinTreningRegistration joinTreningRegistration = messageHelper.getMessage(message, JoinTreningRegistration.class);
        String username = notifikacijeService.getUsername(joinTreningRegistration.getKorisnikIdCard());
        emailService.sendSimpleMessage(username, "Join trening", "Registrovali ste se za trening koji pocinje u " + joinTreningRegistration.getStartTrening().toString() + "u sali " + joinTreningRegistration.getSalaNaziv());
        notifikacijeService.createNotifikacija(username, "Join trening", "Regular", LocalDateTime.now(), UUID.randomUUID().toString(),"SENT");
        notifikacijeService.createNotifikacija(username, "Reminder", "Regular",joinTreningRegistration.getStartTrening(), UUID.randomUUID().toString(),"WAITING");
    }

    @JmsListener(destination = "cancleTreningMangaer", concurrency = "5-10")
    public void cancleTreningManger(Message message) throws JMSException {

        CancleTrening cancleTrening = messageHelper.getMessage(message, CancleTrening.class);
        String manager = notifikacijeService.getUsername(cancleTrening.getManagerIdCard());

        emailService.sendSimpleMessage(manager, "Cancle  trening", "Trening je otkazan ste se za trening koji pocinje u " + cancleTrening.getStartTrening().toString() + " u sali " + cancleTrening.getSalaNaziv());
        notifikacijeService.createNotifikacija(manager, "Cancle trening", "Manager",LocalDateTime.now(), UUID.randomUUID().toString(),"SENT");
    }

    @JmsListener(destination = "cancleTrening", concurrency = "5-20")
    public void cancleTrening(Message message) throws JMSException {

        CancleTrening cancleTrening = messageHelper.getMessage(message, CancleTrening.class);
        String username = notifikacijeService.getUsername(cancleTrening.getKorisnikIdCard());

        emailService.sendSimpleMessage(username, "Cancle  trening", "Trening je otkazan ste se za trening koji pocinje u " + cancleTrening.getStartTrening().toString() + " u sali " + cancleTrening.getSalaNaziv());

        notifikacijeService.createNotifikacija(username, "Cancle trening", "Regular", LocalDateTime.now(), UUID.randomUUID().toString(),"SENT");
    }

}
