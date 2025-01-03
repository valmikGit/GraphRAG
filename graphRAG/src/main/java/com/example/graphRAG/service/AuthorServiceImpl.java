package com.example.graphRAG.service;

import com.example.graphRAG.dto.AuthorDto;
import com.example.graphRAG.entity.Author;
import com.example.graphRAG.exception.AuthorAlreadyExistsException;
import com.example.graphRAG.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NLPService nlpService;

    @Override
    public AuthorDto addAuthor(String name) {
        if (authorRepository.findByName(name).isPresent()) {
            throw new AuthorAlreadyExistsException("Author with name '" + name + "' already exists.");
        }
        Author author = new Author();
        author.setName(name);
        author.setVectorEmbedding(nlpService.generateEmbedding(author.getName()));
        return authorRepository.save(author).convertToDto();
    }

    @Override
    public AuthorDto getAuthorById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(Author::convertToDto).orElse(null);
    }

    @Override
    public AuthorDto updateAuthorName(long id, String name) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            author.get().setName(name);
            author.get().setVectorEmbedding(nlpService.generateEmbedding(author.get().getName()));
            return authorRepository.save(author.get()).convertToDto();
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteAuthor(long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
        }
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(Author::convertToDto)
                .toList();
    }
}
