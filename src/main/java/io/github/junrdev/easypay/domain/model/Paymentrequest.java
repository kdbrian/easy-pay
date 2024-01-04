package io.github.junrdev.easypay.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paymentrequest {

    private String businessShortCode;
    private String password;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
    private String transactionType = "CustomerPayBillOnline";

    private String amount;
    private String partyA;
    private String partyB;
    private String phoneNumber;
    private String callBackURL;
    private String accountReference;
    private String transactionDesc;

}
