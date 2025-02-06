package com.example.vite_project_backend.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String message) {
    super(message);
  }
}
