package com.customer.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTO {
   @NotBlank
   private String firstname;
   @NotBlank
   private String lastname;
   @NotBlank
   private String customerNumber;
}
