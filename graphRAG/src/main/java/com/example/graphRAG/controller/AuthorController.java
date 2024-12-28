package com.example.graphRAG.controller;

import com.example.graphRAG.dto.AuthorDto;
import com.example.graphRAG.exception.AuthorAlreadyExistsException;
import com.example.graphRAG.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/author")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        if (authorDto == null) {
            log.error("Author with ID = {} does not exist.", id);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(authorDto);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> postAuthor(@RequestParam("name") String name) {
        try {
            AuthorDto authorDto = authorService.addAuthor(name);
            return ResponseEntity.ok().body(authorDto);
        } catch (AuthorAlreadyExistsException e) {
            log.error("Author with name = {} already exists.", name);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
