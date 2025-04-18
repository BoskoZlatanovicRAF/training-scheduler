package com.raf.SKProjekat2Servis1.services.implementations;

import com.raf.SKProjekat2Servis1.domain.Token;
import com.raf.SKProjekat2Servis1.dto.queueDto.KorisnikRegistracijaQueueDto;
import com.raf.SKProjekat2Servis1.dto.queueDto.KorisnikResetQueueDto;
import com.raf.SKProjekat2Servis1.publisher.MessageHelper;
import com.raf.SKProjekat2Servis1.repository.TokenRepository;
import com.raf.SKProjekat2Servis1.services.TokenService;
import com.raf.SKProjekat2Servis1.services.UserService;
import jakarta.jms.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Setter
@Service
public class JmsServiceImplementation {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${destination.registrationQueue}")
    private String registrationQueue;


    private MessageHelper messageHelper;

    private TokenRepository tokenRepository;

    public JmsServiceImplementation(MessageHelper messageHelper, TokenRepository tokenRepository) {
        this.messageHelper = messageHelper;
        this.tokenRepository = tokenRepository;
    }

    public void sendRegistrationMessage(String ime, String prezime, String username, String tipKorisnika) {
        String token = UUID.randomUUID().toString();

        // Token creation and saving to database

        Token tokenObj = new Token();

        tokenObj.setToken(token);
        tokenObj.setUsername(username);

        tokenObj.setValidTo(java.time.LocalDateTime.now().plusDays(1));
        tokenRepository.save(tokenObj);

        KorisnikRegistracijaQueueDto dto = new KorisnikRegistracijaQueueDto(username, prezime, ime, token, tipKorisnika);
        jmsTemplate.convertAndSend(registrationQueue,messageHelper.createTextMessage(dto));
    }

    public void sendNewPasswordMessage(String username, int id, String tipKorisnika) {
        String token = UUID.randomUUID().toString();

        KorisnikResetQueueDto tokenObj = new KorisnikResetQueueDto();

        tokenObj.setUrl(token);
        tokenObj.setUsername(username);
        tokenObj.setId(id);
        tokenObj.setTipKorisnika(tipKorisnika);


        jmsTemplate.convertAndSend("resetPasswordQueue",messageHelper.createTextMessage(tokenObj));
    }

}
