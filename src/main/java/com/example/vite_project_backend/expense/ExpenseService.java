package com.example.vite_project_backend.expense;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

  private final ExpenseRepository expenseRepository;

  // Get all expenses by username
  public List<Expense> getExpensesByUsername(String username) {
    log.info("Fetching expenses for username: {}", username);
    List<Expense> expenses = expenseRepository.findByUsername(username);
    if (expenses.isEmpty()) {
      log.warn("No expenses found for username: {}", username);
    } else {
      log.info("Fetched {} expenses for username: {}", expenses.size(), username);
    }
    return expenses;
  }

  // Add a new expense
  public Expense addExpense(Expense expense) {
    log.info("Saving new expense: {}", expense);
    try {
      Expense savedExpense = expenseRepository.save(expense);
      log.info("Expense saved successfully with ID: {}", savedExpense.getId());
      return savedExpense;
    } catch (Exception e) {
      log.error("Error occurred while saving expense: {}", e.getMessage(), e);
      throw e; // Re-throw exception to handle it at the controller level
    }
  }
}
