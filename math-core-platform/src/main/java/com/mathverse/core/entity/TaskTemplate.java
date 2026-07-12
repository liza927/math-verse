package com.mathverse.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private Operation operation;
    private Integer complexity;
    private String generationParam;

}
