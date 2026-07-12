package com.mathverse.core.repository;

import com.mathverse.core.entity.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    List<TaskTemplate> findByTopic_NameTopicAndComplexity(String topic,Integer complexity);
}
