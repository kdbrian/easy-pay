package io.github.junrdev.easypay.controller;


import io.github.junrdev.easypay.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/easypay")
public class CallBackUrls {

    private final static Logger LOGGER = LoggerFactory.getLogger(CallBackUrls.class);
    private RestTemplate restTemplate;

    private final PaymentService paymentService;

    @Autowired
    public CallBackUrls(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay/{amount}/{phoneNumber}")
    public void payStkPush(
            @PathVariable(name = "amount") String amount,
            @PathVariable(name = "amount") String phoneNumber
    ){

        Map<String, String> requestBody = new HashMap<>();

        var resp = paymentService.makePayment(
                "7534146",
                phoneNumber,
                amount
        );

        LOGGER.info(resp.toString());

    }

    @GetMapping("/callback")
    public void callBackUrl(){

    }
}
