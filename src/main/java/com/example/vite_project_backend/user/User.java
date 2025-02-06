package com.example.vite_project_backend.user;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "vite_users") // Changed table name to follow database naming conventions
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
  private Long id;

  private String username;
  private String password;
  private String email;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }
}
