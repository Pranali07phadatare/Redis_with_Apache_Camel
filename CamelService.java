package com.neosoft.RedisApacheCamel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CamelService {

    private final CamelSecurityService securityService;

    public void storeToken(String yourTokenHere) {
        securityService.storeToken(yourTokenHere);
    }

    public String retrieveToken() {
        return securityService.retrieveToken();
    }
}
