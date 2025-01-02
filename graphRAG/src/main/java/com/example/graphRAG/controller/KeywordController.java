package com.example.graphRAG.controller;

import com.example.graphRAG.dto.KeywordDto;
import com.example.graphRAG.exception.KeywordAlreadyExistsException;
import com.example.graphRAG.service.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/keyword")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class KeywordController {
    @Autowired
    private KeywordService keywordService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        KeywordDto keywordDto = keywordService.getById(id);
        if (keywordDto == null) {
            log.error("Keyword with the ID = {} does not exist.", id);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(keywordDto);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(keywordService.getAllKeywords());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addKeyword(@RequestParam String word) {
        try {
            KeywordDto keywordDto = keywordService.addKeyword(word);
            return ResponseEntity.ok().body(keywordDto);
        } catch (KeywordAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateKeyword(@RequestParam Long id, @RequestParam String word) {
        try {
            KeywordDto keywordDto = keywordService.updateKeyword(id, word);
            return ResponseEntity.ok().body(keywordDto);
        } catch (KeywordAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteKeyword(@PathVariable Long id) {
        keywordService.deleteKeywordById(id);
        KeywordDto keywordDto = keywordService.getById(id);
        if (keywordDto == null) {
            return ResponseEntity.ok().body("Keyword deleted successfully.");
        } else {
            log.error("There was some error in deleting the kyword with ID = {}", id);
            return  ResponseEntity.badRequest().build();
        }
    }
}
