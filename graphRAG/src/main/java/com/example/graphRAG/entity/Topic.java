package com.example.graphRAG.entity;

import com.example.graphRAG.dto.TopicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double[] vectorEmbedding;

    public TopicDto convertToDto() {
        return new TopicDto(this.getId(), this.getName());
    }
}
