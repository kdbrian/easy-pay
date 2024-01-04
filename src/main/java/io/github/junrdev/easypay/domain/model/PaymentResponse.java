package io.github.junrdev.easypay.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String merchantRequestID;

    private String checkoutRequestID;

    private String responseCode;

    private String responseDescription;

    private String customerMessage;
}
