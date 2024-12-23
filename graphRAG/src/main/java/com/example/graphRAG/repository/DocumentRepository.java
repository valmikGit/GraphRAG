package com.example.graphRAG.repository;

import com.example.graphRAG.entity.Document;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends  Neo4jRepository<Document, Long> {
}
