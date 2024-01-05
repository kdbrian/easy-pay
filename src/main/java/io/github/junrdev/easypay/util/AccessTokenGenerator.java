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

import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

    public String getAccessToken() throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();

        var secret = consumerKey + ":" + consumerSecret;
        byte[] bytes = secret.getBytes("ISO-8859-1");
        String encoded = Base64.getEncoder().encodeToString(bytes);

        headers.set("cache-control", "no-cache");
        headers.set("authorization", "Basic "+encoded);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // request
        HttpEntity<String> request= new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(accessTokenUrl, HttpMethod.GET, request, String.class);

        var token = response.getBody().split("\"");
        LOGGER.info(response.getBody());

        return token[3];
    }
}


//inte
