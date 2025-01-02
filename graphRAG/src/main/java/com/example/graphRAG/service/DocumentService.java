package com.example.graphRAG.service;

import com.example.graphRAG.dto.DocumentDto;

import javax.print.Doc;
import java.util.List;

public interface DocumentService {
    DocumentDto addDocument(String title, String content, long authorId);
    DocumentDto updateContent(Long id, String content);
    DocumentDto updateTitle(Long id, String title);
    DocumentDto getDocumentById(Long id);
    void deleteDocumentById(Long id);
    List<DocumentDto> getAllDocuments();
    DocumentDto addTopicToDocument(long topicId, long id);
    DocumentDto addKeywordToDocument(long keywordId, long id);
}
