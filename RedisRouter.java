package com.neosoft.RedisApacheCamel.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


//@Component
public class RedisRouter extends RouteBuilder {
//    @Override
//    public void configure() throws Exception {
//        from("timer:myTimer?period=55000")
//                .setBody().simple("Hello from Apache Camel!")
//                .setHeader("CamelRedis.key", constant("myList"))
//                .setHeader("CamelRedis.keys",constant("myObject"))
//                .to("spring-redis://localhost:6379?command=LPUSH");
//
//    }

    @Override
    public void configure() throws Exception {
        // Route to fetch data from a Redis list, process it, and then push it to another Redis list
        from("timer:myTimer?period=55000")
                .routeId("RedisDataProcessingRoute")
                .to("direct:fetchDataFromRedis");

        from("direct:fetchDataFromRedis")
                .routeId("FetchDataFromRedisRoute")
                .setHeader("CamelRedis.key", constant("sourceList"))
//                .setHeader("start", constant(13))
                .log(LoggingLevel.INFO, "Exchange headers: ${headers}")
                .log(LoggingLevel.INFO, "Header 'start' value: ${header.start}")// Redis list from which data will be fetched
                .to("spring-redis://localhost:6379?command=LRANGE") // Fetch data from Redis list
                .split(body()) // Split the list into individual elements
                .streaming() // Process elements concurrently
                .to("direct:processData"); // Direct the elements for processing

        from("direct:processData")
                .routeId("ProcessDataRoute")
                .log(LoggingLevel.ERROR, ("Processing message: ${body}")) // Log the message being processed
                // Apply your processing logic here
                .setBody().simple("Processed: ${body}") // For demonstration, simply append "Processed:" to each message
                .to("direct:pushDataToRedis"); // Direct the processed data to be pushed back to Redis

        from("direct:pushDataToRedis")
                .routeId("PushDataToRedisRoute")
                .setHeader("CamelRedis.key", constant("destinationList")) // Redis list to which data will be pushed
                .to("spring-redis://localhost:6379?command=LPUSH") // Push processed data to Redis list
                .log(LoggingLevel.ERROR, "Data pushed to Redis: ${body}"); // Log that data has been pushed to Redis
    }
}



