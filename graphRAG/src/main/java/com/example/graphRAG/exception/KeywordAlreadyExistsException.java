package com.example.graphRAG.exception;

public class KeywordAlreadyExistsException extends RuntimeException {
    public KeywordAlreadyExistsException(String message) {
        super(message);
    }
}
