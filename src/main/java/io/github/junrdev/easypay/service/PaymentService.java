package io.github.junrdev.easypay.service;

import io.github.junrdev.easypay.domain.model.PaymentResponse;
import io.github.junrdev.easypay.domain.model.Paymentrequest;
import io.github.junrdev.easypay.domain.repo.TokenRepository;
import io.github.junrdev.easypay.util.AccessTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final TokenRepository tokenRepository;
    private final AccessTokenGenerator tokenGenerator;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    @Value("${mpesa.consumerKey}")
    private String consumerKey;

    @Value("${mpesa.consumerSecret}")
    private String consumerSecret;

    @Value("${mpesa.accessUrl}")
    private String accessTokenUrl;

    @Autowired
    public PaymentService(TokenRepository tokenRepository, AccessTokenGenerator tokenGenerator, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = tokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    public PaymentResponse makePayment(String businessNumber, String customerNumber, String amount) {

        var request = Paymentrequest.builder()
                .partyA(businessNumber)
                .partyB(customerNumber)
                .amount(amount)
                .callBackURL("")
                .build();

        // bsnNo + passKey + timestamp
        var pwdStr = businessNumber + "hello-world-its-me#@24" + request.getTimestamp();

        String pwd = passwordEncoder.encode(pwdStr);

        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth(consumerKey, consumerSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<PaymentResponse> requestBody = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest",
                HttpMethod.POST,
                requestBody,
                PaymentResponse.class
        ).getBody();
    }
}
