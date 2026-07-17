package com.mathverse.core.service;

import com.mathverse.core.entity.Topic;
import com.mathverse.core.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    void createTopic_shouldSaveNewTopic_whenNameNotTaken() {
        when(topicRepository.findByNameTopic("Алгебра")).thenReturn(Optional.empty());
        when(topicRepository.save(any(Topic.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Topic topic = topicService.createTopic("Алгебра");
        assertThat(topic.getNameTopic()).isEqualTo("Алгебра");
    }

    @Test
    void createTopic_shouldThrowException_whenNameAlreadyTaken() {
        when(topicRepository.findByNameTopic("Алгебра")).thenReturn(Optional.of(new Topic()));
        assertThatThrownBy(() -> topicService.createTopic("Алгебра"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Данная тема уже есть");
    }

    @Test
    void readTopic_shouldReturnAllTopics(){
        when(topicRepository.findAll()).thenReturn(List.of(new Topic(),new Topic()));
        List<Topic> topics=topicService.readTopic();
        assertThat(topics).isNotNull();
        assertThat(topics.size()).isEqualTo(2);
    }


}
