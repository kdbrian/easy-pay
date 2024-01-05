package io.github.junrdev.easypay.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.junrdev.easypay.domain.repo.TokenRepository;
import io.github.junrdev.easypay.util.AccessTokenGenerator;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

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


    public String STKPushSimulation(String businessShortCode, String password, String timestamp, String transactionType, String amount, String phoneNumber, String partyA, String partyB, String callBackURL, String queueTimeOutURL, String accountReference, String transactionDesc) throws IOException {

        JsonArray jsonArray = new JsonArray();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("BusinessShortCode", businessShortCode);
        jsonObject.addProperty("Password", password);
        jsonObject.addProperty("Timestamp", timestamp);
        jsonObject.addProperty("TransactionType", transactionType);
        jsonObject.addProperty("Amount", amount);
        jsonObject.addProperty("PhoneNumber", phoneNumber);
        jsonObject.addProperty("PartyA", partyA);
        jsonObject.addProperty("PartyB", partyB);
        jsonObject.addProperty("CallBackURL", callBackURL);
        jsonObject.addProperty("AccountReference", accountReference);
        jsonObject.addProperty("QueueTimeOutURL", queueTimeOutURL);
        jsonObject.addProperty("TransactionDesc", transactionDesc);

        jsonArray.add(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");

        OkHttpClient client = new OkHttpClient();
        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, requestJson);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + tokenGenerator.getAccessToken())
                .addHeader("cache-control", "no-cache")
                .build();


        Response response = client.newCall(request).execute();
        System.out.println("Response : "+ response.body().string());
        return response.body().string();
    }
}
