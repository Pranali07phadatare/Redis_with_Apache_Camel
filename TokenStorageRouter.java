package com.neosoft.RedisApacheCamel.router;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class TokenStorageRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Route to store security tokens in Redis
        from("direct:storeToken")
                .routeId("TokenStorageRoute")
                .log(LoggingLevel.INFO, "Received token for storage: ${body}") // Log the received token with INFO level
                .setHeader("CamelRedis.key").constant("security_tokens")
                .log(LoggingLevel.DEBUG, "Storing token in Redis: ${body}") // Log the token being stored with DEBUG level
                .setHeader("CamelRedis.command").constant("SET")
//                .process(exchange -> {
//                    String token = exchange.getIn().getBody(String.class);
//                    if (token == null || token.isEmpty()) {
//                        throw new IllegalArgumentException("Token is null or empty");
//                    }
//                    exchange.getIn().setBody(token);
//                })
                .to("spring-redis://127.0.0.1:6379")
                .log(LoggingLevel.INFO, "Token stored successfully in Redis: ${body}"); // Log the success message after storing the token with INFO level


//        from("timer://retrieveTokenTimer?fixedRate=true&period=60000") // Trigger every minute
//                .routeId("TokenRetrievalRoute")
//                .process(exchange -> {
//                    // Retrieve token from Redis using Jedis
//                    String key = "security_tokens";
//                    String token;
//                    try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
//                        token = jedis.get(key);
//                    }
//
//                    // Set the retrieved token in the message body
//                    exchange.getMessage().setBody(token);
//                })
//                .log(LoggingLevel.INFO, "Retrieved token from Redis: ${body}");


        // Route to retrieve security tokens from Redis
//        from("direct:getToken")
//                .routeId("TokenRetrievalRoute")
//                .setHeader("CamelRedis.key").constant("security_tokens") // Redis key to retrieve tokens
//                .setHeader("CamelRedis.command").constant("GET") // Redis command to get a value
//                .to("spring-redis://127.0.0.1:6379")
//                .log("Retrieved token from Redis: ${body}");

        from("direct:getToken")
                .routeId("TokenRetrievalRoute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        // Retrieve token from Redis using Jedis
                        String key = "security_tokens";
                        String token;
                        try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
                            token = jedis.get(key);
                        }

                        // Set the retrieved token in the message body
                        exchange.getMessage().setBody(token);
                    }
                })
                .log(LoggingLevel.INFO, "Retrieved token from Redis: ${body}");

    }
}
