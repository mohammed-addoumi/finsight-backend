package com.example.vite_project_backend.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
    Map<String, String> fieldErrors = new HashMap<>();
    allErrors.forEach(
        objectError -> {
          String field = ((FieldError) objectError).getField();
          String error = objectError.getDefaultMessage();
          fieldErrors.put(field, error);
        });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
  }
}
