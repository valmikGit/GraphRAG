package com.example.graphRAG.service;

import com.example.graphRAG.dto.TopicDto;
import com.example.graphRAG.entity.Topic;
import com.example.graphRAG.exception.TopicAlreadyExistsException;
import com.example.graphRAG.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public TopicDto addTopic(String name) {
        if (topicRepository.findByName(name).isPresent()) {
            throw new TopicAlreadyExistsException("Topic = " + name + " already exists.");
        }
        Topic topic = new Topic();
        topic.setName(name);
        return topicRepository.save(topic).convertToDto();
    }

    @Override
    public TopicDto updateName(Long id, String name) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            topic.get().setName(name);
            return topicRepository.save(topic.get()).convertToDto();
        } else {
            return null;
        }
    }

    @Override
    public void deleteTopicById(long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            topicRepository.deleteById(id);
        }
    }

    @Override
    public TopicDto getTopicById(long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(Topic::convertToDto).orElse(null);
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(Topic::convertToDto)
                .toList();
    }
}
