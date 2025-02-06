package com.example.vite_project_backend.configuration;

import com.example.vite_project_backend.auth.LoginRequest;
import com.example.vite_project_backend.user.User;
import com.example.vite_project_backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User authenticate(LoginRequest input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

    return userRepository.findByUsername(input.getUsername()).orElseThrow();
  }
}
