package com.example.graphRAG.repository;

import com.example.graphRAG.entity.Keyword;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface KeywordRepository extends Neo4jRepository<Keyword, Long> {
    Optional<Keyword> findByWord(String word);
}
