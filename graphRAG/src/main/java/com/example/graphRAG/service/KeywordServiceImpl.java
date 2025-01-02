package com.example.graphRAG.service;

import com.example.graphRAG.dto.KeywordDto;
import com.example.graphRAG.entity.Keyword;
import com.example.graphRAG.exception.KeywordAlreadyExistsException;
import com.example.graphRAG.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class KeywordServiceImpl implements KeywordService {
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private NLPService nlpService;

    @Override
    public KeywordDto addKeyword(String word) {
        if (keywordRepository.findByWord(word).isPresent()) {
            throw new KeywordAlreadyExistsException("Keyword = " + word + " already exists.");
        }
        Keyword keyword = new Keyword();
        keyword.setWord(word);
        keyword.setVectorEmbedding(nlpService.generateEmbedding(keyword.getWord()));
        return keywordRepository.save(keyword).convertToDto();
    }

    @Override
    public KeywordDto updateKeyword(Long id, String word) {
        Optional<Keyword> keyword = keywordRepository.findById(id);
        if (keyword.isPresent()) {
            keyword.get().setWord(word);
            keyword.get().setVectorEmbedding(nlpService.generateEmbedding(keyword.get().getWord()));
            return keyword.get().convertToDto();
        } else {
            return null;
        }
    }

    @Override
    public void deleteKeywordById(Long id) {
        keywordRepository.deleteById(id);
    }

    @Override
    public KeywordDto getById(Long id) {
        Optional<Keyword> keyword = keywordRepository.findById(id);
        return keyword.map(Keyword::convertToDto).orElse(null);
    }

    @Override
    public List<KeywordDto> getAllKeywords() {
        return keywordRepository.findAll()
                .stream()
                .map(Keyword::convertToDto)
                .toList();
    }
}
