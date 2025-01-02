package com.example.graphRAG.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private Long id;
    private String title;
    private String content;
    private List<KeywordDto> keywordDtos = new ArrayList<>();
    private List<TopicDto> topicDtos = new ArrayList<>();
    private AuthorDto authorDto;
}
