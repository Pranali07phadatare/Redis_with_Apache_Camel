package com.neosoft.RedisApacheCamel.service;

import com.neosoft.RedisApacheCamel.model.User;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSearchService {


    @Autowired
    private ProducerTemplate producerTemplate;

    public List<User> searchUsers(String criteria) {
        return producerTemplate.requestBody("direct:searchUsers", criteria, List.class);
    }
}


