package com.raf.SKProjekat2Servis1.publisher;

import com.raf.SKProjekat2Servis1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPublisher {

    private MessageHelper messageHelper;
    private UserService userService;


}
