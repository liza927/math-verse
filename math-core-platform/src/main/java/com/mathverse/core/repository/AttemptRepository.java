package com.mathverse.core.repository;

import com.mathverse.core.dto.TopicDifficultyDto;
import com.mathverse.core.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUser_IdOrderByTimeAnswerDesc(Long userId);

    @Query("SELECT COUNT(DISTINCT a.user.id) FROM Attempt a WHERE a.timeAnswer >= :since")
    long countDistinctActiveUsersSince(@Param("since") LocalDateTime since);

    @Query("SELECT new com.mathverse.core.dto.TopicDifficultyDto(t.nameTopic, COUNT(a), SUM(CASE WHEN a.correct = false THEN 1L ELSE 0L END)) " +
            "FROM Attempt a JOIN a.taskTemplate tt JOIN tt.topic t " +
            "GROUP BY t.nameTopic")
    List<TopicDifficultyDto> findTopicDifficulty();
}
