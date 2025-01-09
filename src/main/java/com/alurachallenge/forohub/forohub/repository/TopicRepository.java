package com.alurachallenge.forohub.forohub.repository;

import com.alurachallenge.forohub.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
