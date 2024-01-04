package io.github.junrdev.easypay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessTokenGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenGenerator.class);

    @Value("${mpesa.consumerKey}")
    private String consumerKey;

    @Value("${mpesa.consumerSecret}")
    private String consumerSecret;

    @Value("${mpesa.accessUrl}")
    private String accessTokenUrl;


    private RestTemplate restTemplate;

    public AccessTokenGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(){
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth(consumerKey, consumerSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // request
        HttpEntity<String> request= new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                accessTokenUrl,
                HttpMethod.GET,
                request,
                String.class
        );

        var token = response.getBody().split("\"");


        LOGGER.info(response.getBody());

        return token[3];
    }
}


//inte
