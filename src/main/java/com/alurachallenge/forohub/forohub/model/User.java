package com.alurachallenge.forohub.forohub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String user;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Topic> topics; // List of topic ids

//    Create relationship between User and Topic
}
