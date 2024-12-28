package com.example.graphRAG.service;

public interface NLPService {
    double[] generateEmbedding(String text);
    String performQuestionAnswering(String query);
}
