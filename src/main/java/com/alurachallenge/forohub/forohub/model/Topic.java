package com.alurachallenge.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Topic")
@Table(name = "topic")
@EqualsAndHashCode(of = "topicId")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;
    private String title;
    private String message;
    private boolean status;
    private String course;
    private LocalDate createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who created the topic

    public Topic(String title, String message, String course, User user) {
        this.title = title;
        this.message = message;
        this.status = true;
        this.course = course;
        this.user = user;
        this.createdAt = LocalDate.now();

    }

    public Topic() {}

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
