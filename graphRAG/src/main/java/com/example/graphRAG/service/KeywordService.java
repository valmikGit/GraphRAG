package com.example.graphRAG.service;

import com.example.graphRAG.dto.KeywordDto;

import java.util.List;

public interface KeywordService {
    KeywordDto addKeyword(String word);
    KeywordDto updateKeyword(Long id, String word);
    void deleteKeywordById(Long id);
    KeywordDto getById(Long id);
    List<KeywordDto> getAllKeywords();
}
