package com.example.graphRAG.entity;
import com.example.graphRAG.dto.DocumentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private double[] titleVectorEmbedding;
    private double[] contentVectorEmbedding;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private Author author;

    @Relationship(type = "RELATED_TO", direction = Relationship.Direction.OUTGOING)
    private List<Topic> topics = new ArrayList<>();

    public DocumentDto convertToDto() {
        return new DocumentDto(this.getTitle(), this.getContent());
    }
}
