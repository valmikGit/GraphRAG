package com.example.graphRAG.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class Document {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private double[] vectorEmbedding;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private Author author;

    @Relationship(type = "RELATED_TO", direction = Relationship.Direction.OUTGOING)
    private List<Topic> topics;

    // Getters and setters
}
