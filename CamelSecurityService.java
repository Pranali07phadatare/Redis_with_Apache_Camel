package com.neosoft.RedisApacheCamel.service;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CamelSecurityService {
    @Autowired
    private CamelContext camelContext; // Autowire the CamelContext to get access to Camel components


    public void storeToken(String token) {
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:storeToken", token);
    }

    public String retrieveToken() {
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        return producerTemplate.requestBody("direct:getToken", null, String.class);
    }

}
