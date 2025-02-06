package com.example.vite_project_backend.expense;

public class ExpenseMapper {

  public static ExpenseDto toExpenseDto(Expense expense) {
    return ExpenseDto.builder()
        .expenseId(expense.getId())
        .date(expense.getDate())
        .description(expense.getDescription())
        .amount(expense.getAmount())
        .paymentMethod(expense.getPaymentMethod())
        .category(expense.getCategory())
        .merchant(expense.getMerchant())
        .status(expense.getStatus())
        .build();
  }

  public static Expense toExpense(ExpenseDto expenseDto) {
    return Expense.builder()
        .id(expenseDto.getExpenseId())
        .date(expenseDto.getDate())
        .description(expenseDto.getDescription())
        .amount(expenseDto.getAmount())
        .category(expenseDto.getCategory())
        .paymentMethod(expenseDto.getPaymentMethod())
        .merchant(expenseDto.getMerchant())
        .status(expenseDto.getStatus())
        .username(expenseDto.getUsername())
        .build();
  }
}
