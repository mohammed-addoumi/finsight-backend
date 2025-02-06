package com.example.vite_project_backend.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "contact")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ContactInfoDto {

  private String name;
  private String sexe;
  private int age;
}
