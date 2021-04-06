package com.customer.serviceimpl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   RestTemplate restTemplate;
   @Autowired
   CustomerRepo customerRepo;
   @Autowired
   private RedisTemplate<String, CustomerDTO> redisCache;
   @Override
   public ResponseEntity<CustomResponse> addCustomer(CustomerDTO customerDTO) {
      
      Customer customer = new Customer();
         Customer customerfromDB = new Customer();
         customerfromDB = customerRepo.findCustomerByCustomernumber(customerDTO.getCustomerNumber());
         if (customerfromDB != null) {
            CustomResponse customresponse = new CustomResponse("record already exists", HttpStatus.CONFLICT, 5003);
            logger.error("customer already exists with customer number {} ",customerDTO.getCustomerNumber());
            return new ResponseEntity<>(customresponse, HttpStatus.CONFLICT);
         }
         customer.setCustomernumber(customerDTO.getCustomerNumber());
         customer.setFirstname(customerDTO.getFirstname());
         customer.setLastname(customerDTO.getLastname());
//         redisCache.opsForHash().put("customer", customer.getCustomernumber(), customer);
         customerRepo.save(customer);
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.CREATED, 5001);
      logger.info("Susccesfully customer created with customer number {} ",customer.getCustomernumber());
      return new ResponseEntity<>(customresponse, HttpStatus.CREATED);
   }
   @Override
   public ResponseEntity<CustomResponse> updateCustomer(CustomerDTO customerDTO) {
      Customer customer = new Customer();
      Customer customerfromDB = new Customer();
      customerfromDB = customerRepo.findCustomerByCustomernumber(customerDTO.getCustomerNumber());
      if (customerfromDB == null) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         logger.error("customer not found with customer number {} ",customerDTO.getCustomerNumber());
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      customer.setFirstname(customerDTO.getFirstname());
      customer.setLastname(customerDTO.getLastname());
      customerfromDB.copyCustomerObject(customer);
      customerRepo.save(customerfromDB);
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.OK, 5001);
      logger.info("Susccesfully customer updated with customer number {} ",customerfromDB.getCustomernumber());
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
   @Override
   public ResponseEntity<CustomResponse> deleteCustomer(String customernumber) {
      Long count = customerRepo.deleteCustomerByCustomernumber(customernumber);
      if (count < 1) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         logger.error("customer not found with customer number {} ",customernumber);
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      CustomResponse customresponse = new CustomResponse("successfully deleted", HttpStatus.OK, 5001);
      logger.info("Susccesfully customer details deleted with customer number {} ",customernumber);
      return new ResponseEntity<>(customresponse, HttpStatus.OK);
   }
   @Override
   public ResponseEntity<CustomResponse> getCustomer(String customernumber) {
      Customer customerfromDB = new Customer();
      customerfromDB = customerRepo.findCustomerByCustomernumber(customernumber);
      if (customerfromDB == null) {
         CustomResponse customresponse = new CustomResponse("record not found", HttpStatus.NOT_FOUND, 5004);
         logger.error("customer not found with customer number {} ",customernumber);
         return new ResponseEntity<>(customresponse, HttpStatus.NOT_FOUND);
      }
      CustomResponse customresponse = new CustomResponse("success", HttpStatus.OK, 5001);
      customresponse.setData(customerfromDB);
      logger.info("Susccesfully customer details returned for customer number {} ",customernumber);
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
