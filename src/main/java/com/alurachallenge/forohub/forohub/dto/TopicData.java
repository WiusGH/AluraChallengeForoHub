package com.alurachallenge.forohub.forohub.dto;

public record TopicData(
        Long topicId,
        String title,
        String message,
        String course,
        String createdAt,
        String username
) {
}
