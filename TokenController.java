package com.neosoft.RedisApacheCamel.controller;

import com.neosoft.RedisApacheCamel.service.CamelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/redis")
public class TokenController {

    @Autowired
    private CamelService camelService;

    @PostMapping("/storeToken")
    public String storeToken(@RequestParam String token) {
        // Call the method to store a token
        camelService.storeToken(token);
        return "Token stored successfully! :-"+token;
    }

    @PostMapping("/retrieveToken")
    public String retrieveToken() {
        // Call the method to retrieve a token
        String token = camelService.retrieveToken();
        return "Retrieved token: " + token;
    }
}
