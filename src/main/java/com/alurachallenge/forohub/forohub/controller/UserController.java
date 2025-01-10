package com.alurachallenge.forohub.forohub.controller;

import com.alurachallenge.forohub.forohub.dto.UserData;
import com.alurachallenge.forohub.forohub.dto.UserRegisterData;
import com.alurachallenge.forohub.forohub.infra.security.JwtData;
import com.alurachallenge.forohub.forohub.infra.security.TokenService;
import com.alurachallenge.forohub.forohub.model.User;
import com.alurachallenge.forohub.forohub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/test")
    public ResponseEntity test(@RequestBody @Valid UserRegisterData userRegisterData) {
        return ResponseEntity.ok(userRegisterData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterData userRegisterData) {
        if (userRepository.findByUsername(userRegisterData.getUsername()) != null ||
                userRepository.findByEmail(userRegisterData.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists");
        }

        String encryptedPassword = passwordEncoder.encode(userRegisterData.getPassword());
        User user = new User(userRegisterData.getUsername(), userRegisterData.getEmail(), encryptedPassword);
        userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserData userData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userData.username(),
                userData.password()
        );
        try {
            var authenticatedUser = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
            return ResponseEntity.ok(new JwtData(JWTtoken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }
}
