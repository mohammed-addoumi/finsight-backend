package com.example.vite_project_backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorApiResponse {

  @JsonProperty("code_status")
  private int codeStatus;

  private String message;
}
