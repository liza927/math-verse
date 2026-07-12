package com.mathverse.core.repository;

import com.mathverse.core.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUser_IdOrderByTimeAnswerDesc(Long userId);
}
