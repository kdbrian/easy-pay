package io.github.junrdev.easypay.controller;

import io.github.junrdev.easypay.domain.model.PaymentResponse;
import io.github.junrdev.easypay.dto.PaymentRequestDto;
import io.github.junrdev.easypay.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/pay")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/{amount}/{phoneNumber}/{businessCode}")
    @PostMapping
    public String payStkPush(
            @RequestBody PaymentRequestDto paymentRequestDto
//            @PathVariable(name = "amount") String amount,
//            @PathVariable(name = "amount") String phoneNumber,
//            @PathVariable(name = "businessCode") String businessCode
    ) throws IOException {

        var time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        LOGGER.info("time "+time);

        var resp = paymentService.STKPushSimulation(
                paymentRequestDto.getBusinessCode(),
                passwordEncoder.encode(paymentRequestDto.getBusinessCode() + "some-pass-key#$!" + time),
                time,
                "CustomerPayBillOnline",
                paymentRequestDto.getAmount(),
                paymentRequestDto.getPhoneNumber(),
                paymentRequestDto.getPhoneNumber(),
                paymentRequestDto.getBusinessCode(),
                "https://6ed4-2c0f-fe38-2324-eb9b-f1d0-1f6-c24a-8585.ngrok-free.app/pay/callback",
                "5000",
                "BrianEasyPay",
                "Paymentoffuck"
        );

        LOGGER.info(resp);

        return resp;
    }

    @GetMapping("/callback")
    public String callBackFunction(@RequestBody PaymentResponse paymentResponse){
        LOGGER.info(paymentResponse.toString());
        return paymentResponse.toString();
    }

}
