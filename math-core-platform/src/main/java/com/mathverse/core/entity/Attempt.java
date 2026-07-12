package com.mathverse.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private String studentAnswer;

    private Boolean correct;

    @Column(nullable = false)
    private String generatedTask;

    @ManyToOne
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplate taskTemplate;

    @Column(nullable = false)
    private LocalDateTime timeAnswer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
