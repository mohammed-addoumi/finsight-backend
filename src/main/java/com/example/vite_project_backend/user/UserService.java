package com.example.vite_project_backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public User validateUser(String username, String rawPassword) {
    User user = userRepository.findByUsername(username);
    if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
      return user;
    }
    return null;
  }

  public void registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public boolean userExists(String username) {
    return userRepository.findByUsername(username) != null;
  }
}
