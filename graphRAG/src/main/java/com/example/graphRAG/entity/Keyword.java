package com.example.graphRAG.entity;

import com.example.graphRAG.dto.KeywordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Node
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;
    private String word;
    private double[] vectorEmbedding;

    public KeywordDto convertToDto() {
        return new KeywordDto(this.getId(), this.getWord());
    }
}
