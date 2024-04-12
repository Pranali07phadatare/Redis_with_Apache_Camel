package com.neosoft.RedisApacheCamel.service;

public class AuthService {

    public boolean authenticate(String username, String password) {
        // Simulate authentication logic
        // In a real-world scenario, you would authenticate against a database or an external service

        // For demonstration purposes, let's say authentication is successful if username and password are "admin"
        return "admin".equals(username) && "admin".equals(password);
    }
}
