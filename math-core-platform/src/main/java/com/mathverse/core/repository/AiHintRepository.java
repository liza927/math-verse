package com.mathverse.core.repository;

import com.mathverse.core.entity.AiHint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiHintRepository extends JpaRepository<AiHint, Long> {
    List<AiHint> findByAttemptId(Long attemptId);
}