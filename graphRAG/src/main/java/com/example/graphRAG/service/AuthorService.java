package com.example.graphRAG.service;

import com.example.graphRAG.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto addAuthor(String name);
    AuthorDto getAuthorById(long id);
    AuthorDto updateAuthorName(long id, String name);
    void deleteAuthor(long id);
    List<AuthorDto> getAllAuthors();
}
