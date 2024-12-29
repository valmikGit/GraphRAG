package com.example.graphRAG.controller;

import com.example.graphRAG.service.NLPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/query")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class QueryController {
    @Autowired
    private NLPService nlpService;

    @PostMapping("/ask")
    public ResponseEntity<?> getAnswer(@RequestParam("query") String query) {
        try {
            return ResponseEntity.ok().body(nlpService.performQuestionAnswering(query));
        } catch (Exception e) {
            log.error("Exception is: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Error error) {
            log.error("Error is: {}", error.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
