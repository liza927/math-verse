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
@Table(name = "ai_hints")
public class AiHint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hints_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private Attempt attempt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String hintText;

}
