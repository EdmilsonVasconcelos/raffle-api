package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.address.AddressDTO;
import br.com.vsc.raffle.dto.customer.CustomerDTO;
import br.com.vsc.raffle.model.Address;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

class CustomerControllerTest {

    public static final String JOHN = "John";

    public static final String JOHN_EMAIL_COM = "john@example.com";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers() {
        Address address1 = Address.builder().city("city 1").uf("pe").build();
        Address address2 = Address.builder().city("city 2").uf("pe").build();

        Customer customer1 = Customer.builder().id(1L).email(JOHN_EMAIL_COM).name(JOHN).address(address1).build();
        Customer customer2 = Customer.builder().id(2L).email("maria@example.com").name("maria").address(address2).build();

        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
        Assertions.assertEquals(customer1.getName(), response.getBody().get(0).getName());
        Assertions.assertEquals(customer2.getEmail(), response.getBody().get(1).getEmail());
        Mockito.verify(customerService, Mockito.times(1)).getAllCustomers();
    }

    @Test
    void getCustomerById() {
        Long customerId = 1L;
        Address address = Address.builder().city("city 1").uf("pe").build();
        Customer customer = Customer.builder().id(1L).email(JOHN_EMAIL_COM).name("john").address(address).build();

        Mockito.when(customerService.findById(customerId)).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(customer.getName(), response.getBody().getName());
        Assertions.assertEquals(customer.getEmail(), response.getBody().getEmail());
        Mockito.verify(customerService, Mockito.times(1)).findById(customerId);
    }

    @Test
    void saveCustomer() {
        AddressDTO addressDTO = AddressDTO.builder().city("city 1").uf("pe").build();
        CustomerDTO request = CustomerDTO.builder().name(JOHN).email(JOHN_EMAIL_COM).address(addressDTO).build();

        Address address = Address.builder().city("city 1").uf("pe").build();
        Customer customer = Customer.builder().id(1L).email(JOHN_EMAIL_COM).name(JOHN).address(address).build();

        Mockito.when(customerService.upsertCustomer(Mockito.any())).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.saveCustomer(request);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(customer.getName(), response.getBody().getName());
        Assertions.assertEquals(customer.getEmail(), response.getBody().getEmail());
        Mockito.verify(customerService, Mockito.times(1)).upsertCustomer(Mockito.any());
    }

    @Test
    void deleteCustomer() {
        Long customerId = 1L;

        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(customerService, Mockito.times(1)).deleteCustomer(customerId);
    }
}