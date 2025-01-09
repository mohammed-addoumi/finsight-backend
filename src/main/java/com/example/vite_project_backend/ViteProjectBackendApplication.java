package com.example.vite_project_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Finance Insight APIs Documentation",
            description = "Finance Insight APIs Documentation",
            version = "1.0.0"))
public class ViteProjectBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(ViteProjectBackendApplication.class, args);
  }
}
