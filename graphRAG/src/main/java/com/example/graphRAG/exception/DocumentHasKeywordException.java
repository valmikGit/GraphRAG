package com.example.graphRAG.exception;

public class DocumentHasKeywordException extends RuntimeException {
    public DocumentHasKeywordException(String message) {
        super(message);
    }
}
