package com.customer.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Customer")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
   @Indexed(unique = true)
   private String customernumber;
   private String firstname;
   private String lastname;
   ObjectId id;

   public void copyCustomerObject(Customer customer) {
      this.setFirstname(customer.getFirstname());
      this.setLastname(customer.getLastname());
   }
}
