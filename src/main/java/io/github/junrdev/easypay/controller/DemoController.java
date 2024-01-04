package io.github.junrdev.easypay.controller;

import io.github.junrdev.easypay.util.AccessTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    private final AccessTokenGenerator generator;

    @Autowired
    public DemoController(AccessTokenGenerator generator) {
        this.generator = generator;
    }

    @GetMapping("/generate-token")
    public String getToken(){
        var token = generator.getAccessToken();
        return token;
    }
}
