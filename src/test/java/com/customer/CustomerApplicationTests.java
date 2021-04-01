package com.customer;

import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.customer.dto.CustomerDTO;
import com.customer.model.Customer;
import com.customer.repository.CustomerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.customer.domain" })
@EnableMongoRepositories(basePackages = { "com.customer.repository" })
@EnableTransactionManagement
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CustomerApplication.class)
class CustomerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
   protected RestTemplate restTemplate;
	@MockBean
   protected CustomerRepo customerRepoMock;
	protected MockMvc mvc;
   @Autowired
   protected WebApplicationContext webApplicationContext;

   protected String mapToJson(Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }

   MvcResult mvcResult = null;
   MockHttpServletResponse result = null;
   private MockRestServiceServer mockServer;
   public static final String CUSTOMERURL = "/customer/";
   public static final String CUSTOMERURLCONTEXT = "/customer/customernumber";
   private Customer customer;
   private CustomerDTO customerDto = new CustomerDTO();
   @BeforeEach
   public void init() {
      try {
         
         mockServer = MockRestServiceServer.createServer(restTemplate);
         mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         customer = new Customer();
         setCustomer(customer);
         setCustomerDto(customerDto);
      } catch (Exception e) {
      }

   }
   private void setCustomer(Customer customer) {
      customer.setFirstname("firstname");
      customer.setLastname("lastname");
      customer.setCustomernumber("customernumber");
   }

   private void setCustomerDto(CustomerDTO customerDto) {
      customerDto.setFirstname("firstname");
      customerDto.setLastname("lastname");
      customerDto.setCustomerNumber("customernumber");
   }
   public MvcResult performPost(String URL, String content) throws Exception {
      return mvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(content))
            .andReturn();
   }
   public MvcResult performPut(String URL, String content) throws Exception {
      return mvc.perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON).content(content))
            .andReturn();
   }

   public MvcResult performGet(String URL) throws Exception {
      return mvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)).andReturn();
   }

   public MvcResult performDelete(String URL, String content) throws Exception {
      return mvc.perform(MockMvcRequestBuilders.delete(URL).contentType(MediaType.APPLICATION_JSON).content(content))
            .andReturn();
   }
   @Test
   public void addCustomerSuccess() throws URISyntaxException, JsonProcessingException {
      try {
         String requestObj = mapToJson(customerDto);
         when(customerRepoMock.findCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn(null);
         when(customerRepoMock.save(Matchers.any(Customer.class))).thenReturn(customer);
         mvcResult = performPost(CUSTOMERURL, requestObj);
         result = mvcResult.getResponse();
         Assert.assertEquals(201, result.getStatus());
      } catch (Exception e) {
      }
   }

   @Test
   public void addCustomerFailure() throws URISyntaxException, JsonProcessingException {
      try {
         String requestObj = mapToJson(customerDto);
         when(customerRepoMock.findCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn(customer);
         mvcResult = performPost(CUSTOMERURL, requestObj);
         result = mvcResult.getResponse();
         Assert.assertEquals(409, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void updateCustomerSuccess() throws URISyntaxException, JsonProcessingException {
      try {
         String requestObj = mapToJson(customerDto);
         when(customerRepoMock.findCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn(customer);
         when(customerRepoMock.save(Matchers.any(Customer.class))).thenReturn(customer);
         mvcResult = performPut(CUSTOMERURL, requestObj);
         result = mvcResult.getResponse();
         Assert.assertEquals(200, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void updateCustomerFailure() throws URISyntaxException, JsonProcessingException {
      try {
         String requestObj = mapToJson(customerDto);
         when(customerRepoMock.findCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn(null);
         mvcResult = performPut(CUSTOMERURL, requestObj);
         result = mvcResult.getResponse();
         Assert.assertEquals(404, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void getCustomerSuccess() throws URISyntaxException, JsonProcessingException {
      try {
         when(customerRepoMock.findCustomerByCustomernumber("customernumber")).thenReturn(customer);
         mvcResult = performGet(CUSTOMERURLCONTEXT);
         result = mvcResult.getResponse();
         Assert.assertEquals(200, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void getCustomerFailure() throws URISyntaxException, JsonProcessingException {
      try {
         when(customerRepoMock.findCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn(null);
         mvcResult = performGet(CUSTOMERURLCONTEXT);
         result = mvcResult.getResponse();
         Assert.assertEquals(404, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void deleteCustomerSuccess() throws URISyntaxException, JsonProcessingException {
      try {
         when(customerRepoMock.deleteCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn((long) 1);
         mvcResult = performDelete(CUSTOMERURLCONTEXT,"random");
         result = mvcResult.getResponse();
         Assert.assertEquals(200, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void deleteCustomerFailure() throws URISyntaxException, JsonProcessingException {
      try {
         when(customerRepoMock.deleteCustomerByCustomernumber(customerDto.getCustomerNumber())).thenReturn((long) 0);
         mvcResult = performDelete(CUSTOMERURLCONTEXT,"random");
         result = mvcResult.getResponse();
         Assert.assertEquals(404, result.getStatus());
      } catch (Exception e) {
      }
   }
   @Test
   public void getCustomersSuccess() throws URISyntaxException, JsonProcessingException {
      try {
         List<Customer> custlist = new ArrayList<>();
         custlist.add(customer);
         when(customerRepoMock.findAll()).thenReturn(custlist);
         mvcResult = performGet(CUSTOMERURL);
         result = mvcResult.getResponse();
         Assert.assertEquals(200, result.getStatus());
      } catch (Exception e) {
      }
   }
}
