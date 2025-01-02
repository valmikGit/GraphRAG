package com.example.graphRAG.entity;

import com.example.graphRAG.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double[] vectorEmbedding;

    public AuthorDto convertToDto() {
        return new AuthorDto(this.getId(), this.getName());
    }
}
