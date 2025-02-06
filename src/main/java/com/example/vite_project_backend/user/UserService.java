package com.example.vite_project_backend.user;

import com.example.vite_project_backend.exceptions.EmailAlreadyExistsException;
import com.example.vite_project_backend.exceptions.UserNotFoundException;
import com.example.vite_project_backend.exceptions.UsernameAlreadyExistsException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public User validateUser(String username, String rawPassword) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    User user =
        optionalUser.orElseThrow(() -> new UserNotFoundException("username or password incorrect"));
    if (passwordEncoder.matches(rawPassword, user.getPassword())) {
      return user;
    } else {
      throw new UserNotFoundException("username or password incorrect");
    }
  }

  public void registerUser(User user) {
    if (userNameAlreadyExists(user.getUsername())) {
      log.warn("Registration failed: Username {} already exists", user.getUsername());
      throw new UsernameAlreadyExistsException("username already exists");
    }

    if (emailAlreadyExists(user.getEmail())) {
      log.warn("Registration failed: email {} already exists", user.getEmail());
      throw new EmailAlreadyExistsException("email already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public boolean userNameAlreadyExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean emailAlreadyExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }
}
