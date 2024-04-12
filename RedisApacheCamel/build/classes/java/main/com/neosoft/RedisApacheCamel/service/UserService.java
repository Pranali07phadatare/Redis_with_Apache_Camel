// UserService.java
package com.neosoft.RedisApacheCamel.service;

import com.neosoft.RedisApacheCamel.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

    List<User> searchUsers(String criteria);
}
