package com.alurachallenge.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Topic")
@Table(name = "topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "topicId")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;
    private String title;
    private String message;
    private LocalDate date;
    private boolean status;
    private String course;
    private LocalDate createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who created the topic
}
