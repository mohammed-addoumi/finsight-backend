package com.example.vite_project_backend.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfo {

  private final ContactInfoDto contactInfoDto;

  @GetMapping
  public ResponseEntity<ContactInfoDto> getContactInfo() throws InterruptedException {
    return ResponseEntity.ok(contactInfoDto);
  }
}
