package com.example.vite_project_backend.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExpenseDto {

  @JsonProperty("expense_id")
  private Long expenseId;

  @NotNull(message = "date should not be empty")
  @Past(message = "date should be in the past")
  private LocalDate date;

  @NotEmpty(message = "category should not be empty")
  private String category;

  @NotNull(message = "amount should not be empty")
  @Digits(integer = 5, fraction = 5, message = "amount should respect 5 integers 5 fractions rule")
  private BigDecimal amount;

  @Size(min = 10, message = "Description should be longer than 10 characters")
  private String description;

  @JsonProperty("payment_method")
  @NotEmpty(message = "payment method should not be empty")
  private String paymentMethod;

  @NotEmpty(message = "merchant should not be empty")
  private String merchant;

  @NotEmpty(message = "status should not be empty")
  private String status;

  @NotEmpty(message = "username should not be empty")
  private String username;
}
