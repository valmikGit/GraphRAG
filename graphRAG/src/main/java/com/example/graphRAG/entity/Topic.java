package com.example.graphRAG.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double[] vectorEmbedding;

    // Getters and setters
}
