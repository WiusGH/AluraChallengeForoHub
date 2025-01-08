package com.alurachallenge.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String topicId;
    private String title;
    private String message;
    private String date;
    private boolean status;
    private String course;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who created the topic
}
