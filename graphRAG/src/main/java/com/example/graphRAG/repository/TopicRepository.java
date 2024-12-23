package com.example.graphRAG.repository;

import com.example.graphRAG.entity.Topic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, Long> {
}
