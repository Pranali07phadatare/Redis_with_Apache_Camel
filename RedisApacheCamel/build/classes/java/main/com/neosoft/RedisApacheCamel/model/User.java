package com.neosoft.RedisApacheCamel.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@Entity
//@Table(schema = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private UUID id;
    private String name;
}
