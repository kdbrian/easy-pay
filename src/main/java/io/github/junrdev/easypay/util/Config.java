package io.github.junrdev.easypay.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Configuration
public class Config {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return
//    }

}
