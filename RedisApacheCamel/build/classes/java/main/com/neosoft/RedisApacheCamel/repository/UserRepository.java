package com.neosoft.RedisApacheCamel.repository;

import com.neosoft.RedisApacheCamel.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User,UUID> {
    List<User> findByNameContainingIgnoreCase(String criteria);
}
