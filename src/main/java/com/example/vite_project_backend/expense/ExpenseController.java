package com.example.vite_project_backend.expense;

import com.example.vite_project_backend.api.ResourcesPaths;
import com.example.vite_project_backend.api.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Expenses API ", description = "Expenses Operations API")
public class ExpenseController {

  private final ExpenseService expenseService;

  // Get all expenses by username
  @Operation(description = "Fetching all expenses for a specific username")
  @ApiResponse(responseCode = "200", description = "SUCCESS")
  @GetMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES_BY_USERNAME)
  public ResponseEntity<ResponseApi<List<ExpenseDto>>> getAllExpensesByUsername(
      @PathVariable String username) throws InterruptedException {
    log.info("Fetching all expenses for username: {}", username);
    List<Expense> expenses = expenseService.getExpensesByUsername(username);
    if (expenses.isEmpty()) {
      log.warn("No expenses found for username: {}", username);
    } else {
      log.info("Found {} expenses for username: {}", expenses.size(), username);
    }
    List<ExpenseDto> expensesDtosList = expenses.stream().map(ExpenseMapper::toExpenseDto).toList();
    ResponseApi<List<ExpenseDto>> responseApi = ResponseApi.ok(expensesDtosList);
    return ResponseEntity.ok(responseApi);
  }

  // Add a new expense
  @PostMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES)
  public ResponseEntity<Expense> addExpense(@RequestBody @Valid ExpenseDto expenseDto) {
    Expense expense = ExpenseMapper.toExpense(expenseDto);
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

  @Operation(description = "Deleting an expense whose id is passed as a path variable")
  @DeleteMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES_BY_EXPENSE_ID)
  public ResponseEntity<Boolean> deleteExpenseByExpenseId(
      @PathVariable @NotNull(message = "ExpenseId should not be null when deleting")
          Long expenseId) {

    log.info("Request received to delete expense with ID: {}", expenseId);

    try {
      Boolean isDeleted = expenseService.deleteExpenseByExpenseId(expenseId);
      if (isDeleted) {
        log.info("Expense with ID: {} successfully deleted.", expenseId);
      } else {
        log.warn("Expense with ID: {} not found or could not be deleted.", expenseId);
      }
      return ResponseEntity.ok(isDeleted);
    } catch (Exception e) {
      log.error("An error occurred while deleting expense with ID: {}", expenseId, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
  }
}
