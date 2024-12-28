package com.example.graphRAG.repository;

import com.example.graphRAG.entity.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepo extends Neo4jRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailId (String emailId);
}