package com.example.graphRAG.controller;

import com.example.graphRAG.dto.DocumentDto;
import com.example.graphRAG.exception.DocumentAlreadyExistsException;
import com.example.graphRAG.exception.DocumentHasKeywordException;
import com.example.graphRAG.exception.DocumentHasTopicException;
import com.example.graphRAG.exception.UserAlreadyExistsException;
import com.example.graphRAG.service.DocumentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@RequestMapping("/api/auth/document")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocument(@PathVariable Long id) {
        DocumentDto documentDto = documentService.getDocumentById(id);
        if (documentDto == null) {
            log.error("Document with ID = {} does not exist.", id);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(documentDto);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDocument(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("authorId") Long authorId
    ) {
        try {
            DocumentDto documentDto = documentService.addDocument(title, content, authorId);
            if (documentDto == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(documentDto);
            }
        } catch (DocumentAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/updateTitle")
    public ResponseEntity<?> updateTitle(@RequestParam("title") String title, @RequestParam("id") Long id) {
        try {
            DocumentDto documentDto = documentService.updateTitle(id, title);
            if (documentDto == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(documentDto);
            }
        } catch (DocumentAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/updateContent")
    public ResponseEntity<?> updateContent(@RequestParam("content") String content, @RequestParam("id") Long id) {
        try {
            DocumentDto documentDto = documentService.updateContent(id, content);
            if (documentDto == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(documentDto);
            }
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocumentById(id);
        DocumentDto documentDto = documentService.getDocumentById(id);
        if (documentDto == null) {
            return ResponseEntity.ok("Document deleted successfully.");
        } else {
            log.error("There was some error in deleting the document with ID = {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok().body(documentService.getAllDocuments());
    }

    @PostMapping("/addTopic")
    public ResponseEntity<?> addTopic(
            @RequestParam("documentId") Long documentId,
            @RequestParam("topicId") Long topicId
    ) {
        try {
            DocumentDto documentDto = documentService.addTopicToDocument(topicId, documentId);
            if (documentDto == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(documentDto);
            }
        } catch (DocumentHasTopicException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/addKeyword")
    public ResponseEntity<?> addKeyword(
            @RequestParam("documentId") Long documentId,
            @RequestParam("keywordId") Long keywordId
    ) {
        try {
            DocumentDto documentDto = documentService.addKeywordToDocument(keywordId, documentId);
            if (documentDto == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(documentDto);
            }
        } catch (DocumentHasKeywordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
