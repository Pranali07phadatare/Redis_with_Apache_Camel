package com.neosoft.RedisApacheCamel.router;

import com.neosoft.RedisApacheCamel.model.User;
import com.neosoft.RedisApacheCamel.repository.UserRepository;
import com.neosoft.RedisApacheCamel.service.AuthService;
import com.neosoft.RedisApacheCamel.service.UserService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserRouter extends RouteBuilder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void configure() throws Exception {
        from("direct:createUser")
                .process(exchange -> {
                    User user = exchange.getIn().getBody(User.class);
                    userService.createUser(user);
                    exchange.getMessage().setBody("User created successfully");
                });

        from("direct:getUser")
                .process(exchange -> {
                    UUID userId = exchange.getIn().getHeader("userId", UUID.class);
                    User user = userService.getUserById(userId);
                    exchange.getMessage().setBody(user);
                });

        from("direct:updateUser")
                .process(exchange -> {
                    User user = exchange.getIn().getBody(User.class);
                    UUID userId = exchange.getIn().getHeader("userId", UUID.class);
                    userService.updateUser(userId, user);
                    exchange.getMessage().setBody("User updated successfully");
                });

        from("direct:deleteUser")
                .process(exchange -> {
                    UUID userId = exchange.getIn().getHeader("userId", UUID.class);
                    userService.deleteUser(userId);
                    exchange.getMessage().setBody("User deleted successfully");
                });

        from("direct:getAllUsers")
                .process(exchange -> {
                    List<User> users = (List<User>) userRepository.findAll();
                    exchange.getMessage().setBody(users);
                });


        from("direct:searchUsers")
                .process(exchange -> {
                    String criteria = exchange.getIn().getHeader("criteria", String.class);
                    List<User> users = userService.searchUsers(criteria);
                    exchange.getMessage().setBody(users);
                });

        // Add other routes for CRUD operations if needed
    }
}


