package com.customer.serviceimpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customer.dto.CustomerDTO;
import com.customer.model.CustomResponse;
import com.customer.model.Customer;
import com.customer.repository.CustomerRepo;
import com.customer.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {
   @Autowired
   RestTemplate restTemplate;
   @Autowired
   CustomerRepo customerRepo;
   @Override
   public ResponseEntity<CustomResponse> addCustomer(CustomerDTO customerDTO) {
      
      Customer customer = new Customer();
         Customer customerfromDB = new Customer();
         customerfromDB = customerRepo.findCustomerByCustomernumber(customerDTO.getCustomerNumber());
         if (customerfromDB != null) {
            CustomResponse customresponse = new CustomResponse("record already exists", HttpStatus.CONFLICT, 5003);
            return new ResponseEntity<>(customresponse, HttpStatus.CONFLICT);
         }
         customer.setCustomernumber(customerDTO.getCustomerNumber());
         customer.setFirstname(customerDTO.getFirstname());
         customer.setLastname(customerDTO.getLastname());
         customerRepo.save(customer);
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.CREATED, 5001);
      return new ResponseEntity<>(customresponse, HttpStatus.CREATED);
   }
   @Override
   public ResponseEntity<CustomResponse> updateCustomer(CustomerDTO customerDTO) {
      Customer customer = new Customer();
      Customer customerfromDB = new Customer();
      customerfromDB = customerRepo.findCustomerByCustomernumber(customerDTO.getCustomerNumber());
      if (customerfromDB == null) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      customer.setFirstname(customerDTO.getFirstname());
      customer.setLastname(customerDTO.getLastname());
      customerfromDB.copyCustomerObject(customer);
      customerRepo.save(customerfromDB);
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.OK, 5001);
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
   @Override
   public ResponseEntity<CustomResponse> deleteCustomer(String customernumber) {
      Long count = customerRepo.deleteCustomerByCustomernumber(customernumber);
      if (count < 1) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      CustomResponse customresponse = new CustomResponse("successfully deleted", HttpStatus.OK, 5001);
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
   @Override
   public ResponseEntity<CustomResponse> getCustomer(String customernumber) {
      Customer customerfromDB = new Customer();
      customerfromDB = customerRepo.findCustomerByCustomernumber(customernumber);
      if (customerfromDB == null) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.OK, 5001);
      customresponse.setData(customerfromDB);
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
   @Override
   public ResponseEntity<CustomResponse> getCustomers() {
      List<Customer> customerList = customerRepo.findAll();
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.OK, 5001);
      customresponse.setHttpStatus(HttpStatus.OK);
      customresponse.setMessage("success");
      customresponse.setData(customerList);
      customresponse.setCode(5001);
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
}
