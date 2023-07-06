package com.tesis.payticket.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.tesis.payticket.config.BraintreeConfig;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {
   
    @Autowired
    BraintreeConfig braintreeConfig;

    public BraintreeGateway getGateway() {
        log.info("BraintreeConfig: " + braintreeConfig.toString());
        return new BraintreeGateway(
                Environment.SANDBOX,
                braintreeConfig.getMerchantId(),
                braintreeConfig.getPublicKey(),
                braintreeConfig.getPrivateKey()
        );
    }

    public String getToken(){
        
        ClientTokenRequest request = new ClientTokenRequest();
        return getGateway().clientToken().generate(request);
    }


}
