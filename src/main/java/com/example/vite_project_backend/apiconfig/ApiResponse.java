package com.example.vite_project_backend.apiconfig;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

  private T data;
  private int statusCode;
  private String errorMessage;
  private String token;
}
