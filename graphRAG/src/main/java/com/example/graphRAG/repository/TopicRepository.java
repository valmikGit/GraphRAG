package com.example.graphRAG.repository;

import com.example.graphRAG.entity.Topic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, Long> {
    Optional<Topic> findByName(String name);
}
