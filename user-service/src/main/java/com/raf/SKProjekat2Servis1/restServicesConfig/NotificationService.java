package com.raf.SKProjekat2Servis1.restServicesConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class NotificationService {

    @Bean
    public RestTemplate notificationRestService() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8085/api"));
        return restTemplate;
    }

}
