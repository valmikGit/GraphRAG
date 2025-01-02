package com.example.graphRAG.service;

import com.example.graphRAG.dto.DocumentDto;
import com.example.graphRAG.entity.Author;
import com.example.graphRAG.entity.Document;
import com.example.graphRAG.entity.Keyword;
import com.example.graphRAG.entity.Topic;
import com.example.graphRAG.exception.DocumentAlreadyExistsException;
import com.example.graphRAG.exception.DocumentHasTopicException;
import com.example.graphRAG.repository.AuthorRepository;
import com.example.graphRAG.repository.DocumentRepository;
import com.example.graphRAG.repository.KeywordRepository;
import com.example.graphRAG.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private NLPService nlpService;

    @Override
    public DocumentDto addDocument(String title, String content, long authorId) {
        if (documentRepository.findByTitle(title).isPresent()) {
            throw new DocumentAlreadyExistsException("Document with title = " + title + " already exists.");
        }
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isPresent()) {
            Document document = new Document();
            document.setContent(content);
            document.setTitle(title);
            document.setTitleVectorEmbedding(nlpService.generateEmbedding(document.getTitle()));
            document.setContentVectorEmbedding(nlpService.generateEmbedding(document.getContent()));
            document.setAuthor(author.get());
            return documentRepository.save(document).convertToDto();
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
            return documentRepository.save(document.get()).convertToDto();
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
            return documentRepository.save(document.get()).convertToDto();
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
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            for (Topic topic : document.get().getTopics()) {
                if (topic.getId() == topicId) {
                    throw new DocumentHasTopicException("This topic already exists in the list of topics " +
                            "associated with this document.");
                }
            }
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

    @Override
    public DocumentDto addKeywordToDocument(long keywordId, long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            for (Keyword keyword : document.get().getKeywords()) {
                if (keyword.getId() == keywordId) {
                    throw new DocumentHasTopicException("This keyword already exists in the list of keywords " +
                            "associated with this document.");
                }
            }
            Optional<Keyword> keyword = keywordRepository.findById(keywordId);
            if (keyword.isPresent()) {
                document.get().getKeywords().add(keyword.get());
                return document.get().convertToDto();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
