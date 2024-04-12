package com.neosoft.RedisApacheCamel.service;

import com.neosoft.RedisApacheCamel.model.User;
import com.neosoft.RedisApacheCamel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    // Simulated in-memory database
    private Map<String, User> userMap = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return userMap.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User createUser(User user) {
        userMap.put(user.getId().toString(), user);
        return user;
    }

    @Override
    public User updateUser(UUID id, User user) {
        if (userMap.containsKey(id)) {
            user.setId(id);
            userMap.put(id.toString(), user);
            return user;
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if (userMap.containsKey(id)) {
            userMap.remove(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }

    @Override
    public List<User> searchUsers(String criteria) {
        // Implement search logic based on the provided criteria
        return userRepository.findByNameContainingIgnoreCase(criteria);
    }
}


