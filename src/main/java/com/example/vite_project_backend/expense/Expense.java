package com.example.vite_project_backend.expense;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq")
  @SequenceGenerator(name = "expense_seq", sequenceName = "expense_seq", allocationSize = 1)
  private Long id;

  private LocalDate date;

  private String category;

  private BigDecimal amount;

  private String description;

  @Column(name = "payment_method")
  private String paymentMethod;

  private String merchant;

  private String status;

  private String username;
}
