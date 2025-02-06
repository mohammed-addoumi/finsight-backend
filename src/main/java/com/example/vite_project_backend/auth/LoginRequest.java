package com.example.vite_project_backend.auth;

import lombok.Data;

@Data
public class LoginRequest {
  private String username;
  private String password;
}
