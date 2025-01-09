package com.example.vite_project_backend.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ResourcesPaths {

  private static final String BASE_PATH = "/api/v1";

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Expenses {
    public static final String ENDPOINT_API_EXPENSES = BASE_PATH + "/expenses";
    public static final String ENDPOINT_API_EXPENSES_BY_USERNAME =
        ENDPOINT_API_EXPENSES + "/{username}";
    public static final String ENDPOINT_API_EXPENSES_BY_EXPENSE_ID =
        ENDPOINT_API_EXPENSES + "/{expenseId}";
  }
}
