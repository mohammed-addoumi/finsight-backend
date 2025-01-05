package com.example.vite_project_backend.auth;

import com.example.vite_project_backend.apiconfig.ApiResponse;
import com.example.vite_project_backend.user.User;
import com.example.vite_project_backend.user.UserService;
import java.util.Objects;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Allow specific origin
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest loginRequest) {
    logger.info("Login attempt for username: {}", loginRequest.getUsername());

    User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());

    if (Objects.nonNull(user)) {
      logger.info("user logged in successfully");
      ApiResponse<Object> apiResponse = ApiResponse.builder().data(user).build();
      return ResponseEntity.ok(apiResponse);
    } else {
      logger.warn("Login failed for username: {}", loginRequest.getUsername());
      ApiResponse<Object> apiResponse =
          ApiResponse.builder()
              .errorMessage("username or password incorrect")
              .statusCode(HttpStatus.NOT_FOUND.value())
              .build();
      return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    logger.info("Registration attempt for username: {}", user.getUsername());

    if (userService.userExists(user.getUsername())) {
      logger.warn("Registration failed: Username {} already exists", user.getUsername());
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
    }

    userService.registerUser(user);
    logger.info("User registered successfully: {}", user.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }
}

@Data
class LoginRequest {
  private String username;
  private String password;
}
