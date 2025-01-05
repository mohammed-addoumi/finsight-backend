package com.example.vite_project_backend.expense;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  List<Expense> findByUsername(String username);
}
