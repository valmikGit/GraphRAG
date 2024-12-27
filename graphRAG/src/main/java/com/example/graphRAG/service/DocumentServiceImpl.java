package com.example.graphRAG.service;

import com.example.graphRAG.dto.AuthorDto;
import com.example.graphRAG.dto.DocumentDto;
import com.example.graphRAG.entity.Author;
import com.example.graphRAG.entity.Document;
import com.example.graphRAG.entity.Topic;
import com.example.graphRAG.repository.AuthorRepository;
import com.example.graphRAG.repository.DocumentRepository;
import com.example.graphRAG.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private NLPService nlpService;

    @Override
    public DocumentDto addDocument(String title, String content, long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isPresent()) {
            Document document = new Document();
            document.setContent(content);
            document.setTitle(title);
            document.setTitleVectorEmbedding(nlpService.generateEmbedding(document.getTitle()));
            document.setContentVectorEmbedding(nlpService.generateEmbedding(document.getContent()));
            document.setAuthor(author.get());
            documentRepository.save(document);
            return document.convertToDto();
        } else {
            return null;
        }
    }

    @Override
    public DocumentDto updateContent(Long id, String content) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            document.get().setContent(content);
            document.get().setContentVectorEmbedding(nlpService.generateEmbedding(document.get().getContent()));
            documentRepository.save(document.get());
            return document.get().convertToDto();
        }
        else {
            return null;
        }
    }

    @Override
    public DocumentDto updateTitle(Long id, String title) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            document.get().setTitle(title);
            document.get().setTitleVectorEmbedding(nlpService.generateEmbedding(document.get().getTitle()));
            documentRepository.save(document.get());
            return document.get().convertToDto();
        }
        else {
            return null;
        }
    }

    @Override
    public DocumentDto getDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        return document.map(Document::convertToDto).orElse(null);
    }

    @Override
    public void deleteDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.deleteById(id);
        }
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(Document::convertToDto)
                .toList();
    }

    @Override
    public DocumentDto addTopicToDocument(long topicId, long id) {
        Optional<Document> document = documentRepository.findById(topicId);
        if (document.isPresent()) {
            Optional<Topic> topic = topicRepository.findById(topicId);
            if (topic.isPresent()) {
                document.get().getTopics().add(topic.get());
                return document.get().convertToDto();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
