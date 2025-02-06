package com.example.vite_project_backend.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${cors.allowed-origins:}")
  private String corsAllowedOrigins;

  public SecurityConfig(
      JwtAuthenticationFilter jwtAuthenticationFilter,
      AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests()
        .requestMatchers("/api/auth/**")
        .permitAll()
        .requestMatchers("/actuator/**")
        .permitAll()
        .requestMatchers("/api/v1/contact-info")
        .permitAll()
        .requestMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    System.out.println("cors allowd origins : " + corsAllowedOrigins);
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(corsAllowedOrigins.split(",")));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    configuration.setAllowCredentials(true); // Allow cookies or credentials

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
