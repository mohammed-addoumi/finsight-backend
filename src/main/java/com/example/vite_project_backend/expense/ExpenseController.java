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
@CrossOrigin("*")
@Tag(name = "Expenses API ", description = "Expenses Operations API")
public class ExpenseController {

  private final ExpenseService expenseService;

  // Get all expenses by username
  @Operation(
      summary = "Fetch all expenses for a specific username",
      description = "Retrieves all expenses associated with the given username.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "SUCCESS - Expenses were successfully retrieved."),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST - The username is invalid."),
        @ApiResponse(
            responseCode = "404",
            description = "NOT FOUND - No expenses found for the given username."),
        @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR - An unexpected error occurred.")
      })
  @GetMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES_BY_USERNAME)
  public ResponseEntity<ResponseApi<List<ExpenseDto>>> getAllExpensesByUsername(
      @PathVariable String username) {
    log.info("Request received to fetch all expenses for username: {}", username);
    if (1 == 1) {
      throw new IllegalArgumentException("test circuit breaker");
    }
    try {
      List<Expense> expenses = expenseService.getExpensesByUsername(username);

      if (expenses.isEmpty()) {
        log.warn("No expenses found for username: {}", username);
      }

      log.info("Found {} expenses for username: {}", expenses.size(), username);
      List<ExpenseDto> expensesDtosList =
          expenses.stream().map(ExpenseMapper::toExpenseDto).toList();

      ResponseApi<List<ExpenseDto>> responseApi = ResponseApi.ok(expensesDtosList);
      return ResponseEntity.ok(responseApi);
    } catch (IllegalArgumentException e) {
      log.error("Invalid username provided: {}", username, e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseApi.error("Invalid username: " + username));
    } catch (Exception e) {
      log.error(
          "An unexpected error occurred while fetching expenses for username: {}", username, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseApi.error("An unexpected error occurred. Please try again later."));
    }
  }

  @Operation(
      summary = "Fetch an expense by ID",
      description = "Retrieves the details of a specific expense using the provided expense ID.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "SUCCESS - The expense was successfully retrieved."),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST - The input ID is invalid."),
        @ApiResponse(
            responseCode = "404",
            description = "NOT FOUND - No expense exists with the given ID."),
        @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR - An unexpected error occurred.")
      })
  @GetMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES)
  public ResponseEntity<ResponseApi<ExpenseDto>> getExpenseById(@RequestParam Long id) {
    log.info("Request received to fetch expense with ID: {}", id);
    try {
      Expense expense = expenseService.getExpenseById(id);
      if (expense == null) {
        log.warn("No expense found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      ExpenseDto expenseDto = ExpenseMapper.toExpenseDto(expense);
      log.info("Successfully fetched expense with ID: {}", id);
      ResponseApi<ExpenseDto> responseApi = ResponseApi.ok(expenseDto);
      return ResponseEntity.ok(responseApi);
    } catch (IllegalArgumentException e) {
      log.error("Invalid ID provided: {}", id, e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
      log.error("An error occurred while fetching expense with ID: {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Operation(
      summary = "Add a new expense",
      description = "Adds a new expense to the system using the provided expense data.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "SUCCESS - The expense was successfully added."),
        @ApiResponse(
            responseCode = "400",
            description = "BAD REQUEST - The input data is invalid."),
        @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR - An unexpected error occurred.")
      })
  @PostMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES)
  public ResponseEntity<Expense> addExpense(@RequestBody @Valid ExpenseDto expenseDto) {
    log.info("Request received to add a new expense: {}", expenseDto);
    try {
      Expense expense = ExpenseMapper.toExpense(expenseDto);
      log.debug("Converted ExpenseDto to Expense: {}", expense);

      Expense savedExpense = expenseService.addExpense(expense);
      log.info("Expense added successfully with ID: {}", savedExpense.getId());

      return ResponseEntity.ok(savedExpense);
    } catch (Exception e) {
      log.error("Error occurred while adding expense: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Operation(
      summary = "Delete an expense by its ID",
      description = "Deletes a specific expense based on the provided expense ID.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "SUCCESS - Expense was successfully deleted."),
        @ApiResponse(
            responseCode = "400",
            description = "BAD REQUEST - The provided expense ID is invalid."),
        @ApiResponse(
            responseCode = "404",
            description = "NOT FOUND - No expense found with the given ID."),
        @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR - An unexpected error occurred.")
      })
  @DeleteMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES_BY_EXPENSE_ID)
  public ResponseEntity<Boolean> deleteExpenseByExpenseId(
      @PathVariable @NotNull(message = "ExpenseId should not be null when deleting")
          Long expenseId) {

    log.info("Request received to delete expense with ID: {}", expenseId);
    try {
      Boolean isDeleted = expenseService.deleteExpenseByExpenseId(expenseId);

      if (isDeleted) {
        log.info("Expense with ID: {} successfully deleted.", expenseId);
        return ResponseEntity.ok(true);
      } else {
        log.warn("Expense with ID: {} not found or could not be deleted.", expenseId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } catch (IllegalArgumentException e) {
      log.error("Invalid expense ID provided: {}", expenseId, e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    } catch (Exception e) {
      log.error("An unexpected error occurred while deleting expense with ID: {}", expenseId, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
  }

  @Operation(
      summary = "Update an existing expense",
      description =
          "Updates the details of an expense using the provided expense data. The expense ID must be included in the request body.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "SUCCESS - The expense was successfully updated."),
        @ApiResponse(
            responseCode = "400",
            description = "BAD REQUEST - The input data is invalid."),
        @ApiResponse(
            responseCode = "404",
            description = "NOT FOUND - The expense ID does not exist."),
        @ApiResponse(
            responseCode = "500",
            description = "INTERNAL SERVER ERROR - An unexpected error occurred.")
      })
  @PutMapping(path = ResourcesPaths.Expenses.ENDPOINT_API_EXPENSES)
  public ResponseEntity<Boolean> updateExpense(@RequestBody @Valid ExpenseDto expenseDto) {
    log.info("Request received to update expense with ID: {}", expenseDto.getExpenseId());
    try {
      Expense expense = ExpenseMapper.toExpense(expenseDto);
      Boolean isUpdated = expenseService.updateExpense(expense);

      if (isUpdated) {
        log.info("Expense with ID: {} successfully updated.", expenseDto.getExpenseId());
      } else {
        log.warn(
            "Expense with ID: {} not found or could not be updated.", expenseDto.getExpenseId());
      }

      return ResponseEntity.ok(isUpdated);
    } catch (Exception e) {
      log.error(
          "An error occurred while updating expense with ID: {}", expenseDto.getExpenseId(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
  }
}
