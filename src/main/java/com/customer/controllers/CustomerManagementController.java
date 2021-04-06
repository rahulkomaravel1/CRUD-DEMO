package com.customer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dto.CustomerDTO;
import com.customer.model.CustomResponse;
import com.customer.service.CustomerService;

@RestController
@RequestMapping({"/customer"})
public class CustomerManagementController {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   private CustomerService customerservice;
   @PostMapping(value = "/", produces = "application/json")
   public ResponseEntity<CustomResponse> addCustomer(@Valid @RequestBody CustomerDTO Customerdto,
         HttpServletRequest request) {
      ResponseEntity<CustomResponse> response = customerservice.addCustomer(Customerdto);
      return response;
   }
   @PutMapping(value = "/", produces = "application/json")
   public ResponseEntity<CustomResponse> updateCustomer(@Valid @RequestBody CustomerDTO Customerdto,
         HttpServletRequest request) {
      ResponseEntity<CustomResponse> response = customerservice.updateCustomer(Customerdto);
      return response;
   }
   @DeleteMapping(value = "/{customernumber}", produces = "application/json")
   public ResponseEntity<CustomResponse> deleteCustomer(@Valid @PathVariable("customernumber") String customernumber,
         HttpServletRequest request) {
      ResponseEntity<CustomResponse> response = customerservice.deleteCustomer(customernumber);
      return response;
   }
   @GetMapping(value = "/{customernumber}", produces = "application/json")
   public ResponseEntity<CustomResponse> getCustomer(@Valid @PathVariable("customernumber") String customernumber,
         HttpServletRequest request) {
      ResponseEntity<CustomResponse> response = customerservice.getCustomer(customernumber);
      return response;
   }
   @GetMapping(value = "/", produces = "application/json")
   public ResponseEntity<CustomResponse> getCustomers(HttpServletRequest request) {
      ResponseEntity<CustomResponse> response = customerservice.getCustomers();
      return response;
   }
}
