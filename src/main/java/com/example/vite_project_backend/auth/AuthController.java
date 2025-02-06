package com.example.vite_project_backend.auth;

import com.example.vite_project_backend.configuration.AuthenticationService;
import com.example.vite_project_backend.user.User;
import com.example.vite_project_backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final AuthenticationService authenticationService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    log.info("Login attempt for username: {}", loginRequest.getUsername());
    User authenticatedUser = authenticationService.authenticate(loginRequest);
    String jwtToken = jwtUtil.generateToken(authenticatedUser);
    LoginResponse loginResponse =
        LoginResponse.builder()
            .username(authenticatedUser.getUsername())
            .token(jwtToken)
            .expiresIn(jwtUtil.getExpirationTime())
            .build();
    return ResponseEntity.ok(loginResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    log.info("Registration attempt for username: {}", user.getUsername());
    userService.registerUser(user);
    log.info("User registered successfully: {}", user.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }
}
