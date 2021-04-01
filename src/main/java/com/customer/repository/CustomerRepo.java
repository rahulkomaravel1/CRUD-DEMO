package com.customer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.customer.model.Customer;

public interface CustomerRepo extends MongoRepository<Customer, String> {

   public Customer findCustomerByCustomernumber(String customernumber);

   public long deleteCustomerByCustomernumber(String customernumber);

   @Override
   public List<Customer> findAll();
}
