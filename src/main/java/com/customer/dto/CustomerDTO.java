package com.customer.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTO {
   private String firstname;
   private String lastname;
   private String customerNumber;
}
