package com.alurachallenge.forohub.forohub.controller;


import com.alurachallenge.forohub.forohub.dto.TopicRegisterData;
import com.alurachallenge.forohub.forohub.model.Topic;
import com.alurachallenge.forohub.forohub.model.User;
import com.alurachallenge.forohub.forohub.repository.TopicRepository;
import com.alurachallenge.forohub.forohub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTopic(@RequestBody @Valid TopicRegisterData topicRegisterData, Authentication authentication) {

        String username = authentication.getName();

        UserDetails loggedInUser = userRepository.findByUsername(username);
        if (loggedInUser == null) {
            return ResponseEntity.status(403).body("User not found or unauthorized");
        }

        Topic topic = new Topic(topicRegisterData.title(), topicRegisterData.message(),
                topicRegisterData.course(), (User) loggedInUser);
        topicRepository.save(topic);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topic.getTopicId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
