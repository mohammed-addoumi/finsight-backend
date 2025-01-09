package com.example.vite_project_backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseApi<T> {

  private T data;

  @JsonProperty("code_status")
  private int codeStatus;

  private String message;

  private ResponseApi() {}

  public static <T> ResponseApi<T> ok(T data) {
    ResponseApi<T> responseApi = new ResponseApi<>();
    responseApi.setData(data);
    responseApi.setCodeStatus(HttpStatus.OK.value());
    responseApi.setMessage("data fetched successefuly");
    return responseApi;
  }
}
