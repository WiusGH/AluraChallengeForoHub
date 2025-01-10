package com.alurachallenge.forohub.forohub.controller;


import com.alurachallenge.forohub.forohub.dto.TopicData;
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
import java.util.List;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable("id") Long id, Authentication authentication) {
        String username = authentication.getName();

        UserDetails loggedInUser = userRepository.findByUsername(username);
        if (loggedInUser == null) {
            return ResponseEntity.status(403).body("User not found or unauthorized");
        }

        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.status(404).body("Topic not found");
        }

        System.out.println(username);
        System.out.println(topic.getUser().getUsername());
        if (!topic.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).body("User not authorized to delete this topic");
        }

        topicRepository.deleteById(id);
        return ResponseEntity.ok("Topic successfully deleted");
    }

    @GetMapping("/get-all-topics")
    public ResponseEntity<?> getAllTopics(Authentication authentication) {
        String username = authentication.getName();

        UserDetails loggedInUser = userRepository.findByUsername(username);
        if (loggedInUser == null) {
            return ResponseEntity.status(403).body("User not found or unauthorized");
        }

        List<TopicData> topics = topicRepository.findAll().stream()
                .map(topic -> new TopicData(
                        topic.getTopicId(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCourse(),
                        topic.getCreatedAt().toString(),
                        topic.getUser().getUsername()
                ))
                .toList();

        return ResponseEntity.ok(topics);
    }

}
