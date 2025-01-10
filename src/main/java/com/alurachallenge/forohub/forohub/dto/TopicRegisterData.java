package com.alurachallenge.forohub.forohub.dto;

import com.alurachallenge.forohub.forohub.model.User;

public record TopicRegisterData(String title, String message, String course, User user) {
}
