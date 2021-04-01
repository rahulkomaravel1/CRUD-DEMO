package com.customer.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

   private Integer code;
   private String message;
   private HttpStatus httpStatus;

   private Object data;
   private String debugMessage;

   public CustomResponse(HttpStatus status) {
      this.httpStatus = status;
   }

   public CustomResponse(String message, HttpStatus status) {
      this.message = message;
      this.httpStatus = status;
   }

   public CustomResponse(String message, HttpStatus status, Integer code) {
      this.message = message;
      this.httpStatus = status;
      this.code = code;
   }

   public CustomResponse(HttpStatus status, String message, Throwable ex) {
      this.httpStatus = status;
      this.message = message;
      this.debugMessage = ex.getLocalizedMessage();
   }
}
