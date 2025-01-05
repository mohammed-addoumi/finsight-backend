package com.example.vite_project_backend.expense;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

  private final ExpenseService expenseService;

  // Get all expenses by username
  @GetMapping("/{username}")
  public ResponseEntity<List<Expense>> getAllExpensesByUsername(@PathVariable String username)
      throws InterruptedException {
    log.info("Fetching all expenses for username: {}", username);
    List<Expense> expenses = expenseService.getExpensesByUsername(username);
    if (expenses.isEmpty()) {
      log.warn("No expenses found for username: {}", username);
    } else {
      log.info("Found {} expenses for username: {}", expenses.size(), username);
    }
    return ResponseEntity.ok(expenses);
  }

  // Add a new expense
  @PostMapping
  public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
    log.info("Adding a new expense: {}", expense);
    try {
      Expense savedExpense = expenseService.addExpense(expense);
      log.info("Expense added successfully with ID: {}", savedExpense.getId());
      return ResponseEntity.ok(savedExpense);
    } catch (Exception e) {
      log.error("Error occurred while adding expense: {}", e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
