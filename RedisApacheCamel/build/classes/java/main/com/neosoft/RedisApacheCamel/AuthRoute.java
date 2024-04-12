package com.neosoft.RedisApacheCamel.router;

import com.neosoft.RedisApacheCamel.service.AuthService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRoute extends RouteBuilder {
    @Autowired
    private AuthService authService;

    @Override
    public void configure() {
        from("direct:authenticate")
                .process(exchange -> {
                    String username = exchange.getIn().getHeader("username", String.class);
                    String password = exchange.getIn().getHeader("password", String.class);
                    boolean authenticated = authService.authenticate(username, password);
                    exchange.getMessage().setHeader("authenticated", authenticated);
                });
    }
}
