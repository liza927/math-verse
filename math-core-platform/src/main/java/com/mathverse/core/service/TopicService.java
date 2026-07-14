package com.mathverse.core.service;

import com.mathverse.core.entity.Topic;
import com.mathverse.core.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic createTopic(String topic){
        if(topicRepository.findByNameTopic(topic).isPresent()){
            throw new RuntimeException("Данная тема уже есть");
        } else {
            Topic newTopic = new Topic();
            newTopic.setNameTopic(topic);
            return topicRepository.save(newTopic);
        }
    }

    public List<Topic> readTopic(){
        return topicRepository.findAll();
    }
}
