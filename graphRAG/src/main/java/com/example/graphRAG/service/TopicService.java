package com.example.graphRAG.service;

import com.example.graphRAG.dto.TopicDto;

import java.util.List;

public interface TopicService {
    TopicDto addTopic(String name);
    TopicDto updateName(Long id, String name);
    void deleteTopicById(long id);
    TopicDto getTopicById(long id);
    List<TopicDto> getAllTopics();
}
