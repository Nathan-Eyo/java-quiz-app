package com.assignment.quizzy.service;

import com.assignment.quizzy.config.JwtUtil;
import com.assignment.quizzy.controller.AuthenticationRequest;
import com.assignment.quizzy.controller.AuthenticationResponse;
import com.assignment.quizzy.controller.RegisterRequest;
import com.assignment.quizzy.model.Role;
import com.assignment.quizzy.model.User;
import com.assignment.quizzy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwt = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwt = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
