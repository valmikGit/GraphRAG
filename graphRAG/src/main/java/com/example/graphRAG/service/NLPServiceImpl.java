package com.example.graphRAG.service;

import com.example.graphRAG.entity.Author;
import com.example.graphRAG.entity.Document;
import com.example.graphRAG.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

@Service
public class NLPServiceImpl implements NLPService {

    private static final String PYTHON_EMBEDDING_URL = "http://localhost:5000/embedding";
    private static final String PYTHON_QA_URL = "http://localhost:5000/qa";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    public double[] generateEmbedding(String text) {
        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare the request body
            String requestBody = "{\"text\": \"" + text + "\"}";

            // Create HttpEntity with headers and body
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Perform POST request
            ResponseEntity<String> response = restTemplate.postForEntity(PYTHON_EMBEDDING_URL, requestEntity, String.class);

            // Parse response into a double array
            return parseJsonToArray(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get embedding", e);
        }
    }

    public String performQuestionAnswering(String query, String context) {
        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare the request body
            String requestBody = "{\"query\": \"" + query + "\", \"context\": \"" + context + "\"}";

            // Create HttpEntity with headers and body
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Perform POST request
            ResponseEntity<String> response = restTemplate.postForEntity(PYTHON_QA_URL, requestEntity, String.class);

            // Return the response body
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to perform QA", e);
        }
    }

    private double[] parseJsonToArray(String json) {
        try {
            // Use Jackson's ObjectMapper to parse JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON string to double array
            return objectMapper.readValue(json, double[].class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON to double array", e);
        }
    }

    @PostConstruct
    public void init() {
        Author author = new Author();
        author.setName("Random Author");
        double[] random_Vector = {0.0, 0.0};
        author.setVectorEmbedding(random_Vector);
        authorRepository.save(author);
    }
}
