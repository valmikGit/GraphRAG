package com.example.graphRAG.controller;

import com.example.graphRAG.dto.TopicDto;
import com.example.graphRAG.exception.TopicAlreadyExistsException;
import com.example.graphRAG.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/topic")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopic(@PathVariable Long id) {
        TopicDto topicDto = topicService.getTopicById(id);
        if (topicDto == null) {
            log.error("Topic with ID = {} does not exist.", id);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(topicDto);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTopics() {
        return ResponseEntity.ok().body(topicService.getAllTopics());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTopic(@RequestParam("name") String name) {
        try {
            TopicDto topicDto = topicService.addTopic(name);
            return ResponseEntity.ok().body(topicDto);
        } catch (TopicAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopicById(id);
        TopicDto topicDto = topicService.getTopicById(id);
        if (topicDto == null) {
            return ResponseEntity.ok("Topic deleted successfully.");
        } else {
            log.error("There was some error in deleting the topic with ID = {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTopic(@RequestParam("name") String name, @RequestParam("id") Long id) {
        try {
            TopicDto topicDto = topicService.updateName(id, name);
            if (topicDto == null) {
                log.error("Topic with ID = {} does not exist.", id);
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(topicDto);
            }
        } catch (TopicAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
