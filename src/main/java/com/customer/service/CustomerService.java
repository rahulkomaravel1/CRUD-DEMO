package com.customer.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.customer.dto.CustomerDTO;
import com.customer.model.CustomResponse;

public interface CustomerService {
   ResponseEntity<CustomResponse> addCustomer(CustomerDTO customerDTO);

   ResponseEntity<CustomResponse> updateCustomer(CustomerDTO customerDTO);

   ResponseEntity<CustomResponse> deleteCustomer(String customernumber);

   ResponseEntity<CustomResponse> getCustomer(String customernumber);

   ResponseEntity<CustomResponse> getCustomers();
}
