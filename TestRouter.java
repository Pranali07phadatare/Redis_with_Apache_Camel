package com.neosoft.RedisApacheCamel.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TestRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Route to monitor a Redis key for changes and process the updated data
        from("spring-redis://localhost:6379?command=SUBSCRIBE&channels=myChannel")
                .routeId("RedisDataMonitoringRoute")
                .log(LoggingLevel.INFO, "Received Redis update: ${body}")
                .to("direct:processRedisUpdate");

        from("direct:processRedisUpdate")
                .routeId("ProcessRedisUpdateRoute")
                .choice()
                .when(header("CamelRedisChannel").isEqualTo("myChannel"))
                .log("Received update from 'myChannel'")
                .setHeader("CamelRedis.key", constant("myDataKey"))
                .to("spring-redis://localhost:6379?command=GET") // Fetch data from Redis based on the key
                .log(LoggingLevel.INFO, "Retrieved data from Redis: ${body}")
                // Apply your processing logic here
                .setBody().simple("Processed data: ${body}")
                .log(LoggingLevel.INFO, "Processed data: ${body}")
                .to("direct:storeProcessedData")
                .end();

        from("direct:storeProcessedData")
                .routeId("StoreProcessedDataRoute")
                .setHeader("CamelRedis.key", constant("processedDataList")) // Redis list to store processed data
                .to("spring-redis://localhost:6379?command=RPUSH") // Push processed data to Redis list
                .log(LoggingLevel.INFO, "Processed data stored in Redis: ${body}");
    }
}
