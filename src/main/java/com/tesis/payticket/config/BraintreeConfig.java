package com.tesis.payticket.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BraintreeConfig {


    @Value("${braintree.merchant-id}")
    private String merchantId;

    @Value("${braintree.public-key}")
    private String publicKey;

    @Value("${braintree.private-key}")
    private String privateKey;
    
}
