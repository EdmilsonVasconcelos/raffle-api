package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.CustomerDoesNotExistException;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = Customer.builder().id(1L).email("john@example.com").name("john").build();
        Customer customer2 = Customer.builder().id(2L).email("maria@example.com").name("maria").build();

        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        List<Customer> result = customerService.getAllCustomers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(customer1, result.get(0));
        Assertions.assertEquals(customer2, result.get(1));
        Mockito.verify(customerRepository, Mockito.times(2)).findAll();
    }

    @Test
    public void testFindByIdExistingCustomer() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(1L).email("john@example.com").name("john").build();

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(customerId);

        Assertions.assertEquals(customerId, result.getId());
        Assertions.assertEquals("john", result.getName());
        Assertions.assertEquals("john@example.com", result.getEmail());
        Mockito.verify(customerRepository, Mockito.times(1)).findById(customerId);
    }

    @Test
    public void testFindByIdNonExistingCustomer() {
        Long customerId = 1L;

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerDoesNotExistException.class, () -> {
            customerService.findById(customerId);
        });
        Mockito.verify(customerRepository, Mockito.times(1)).findById(customerId);
    }

    @Test
    public void testFindByEmailExistingCustomer() {
        String email = "john@example.com";
        Customer customer = Customer.builder().id(1L).email("old@example.com").name("john").build();

        Mockito.when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        Customer result = customerService.findByEmail(email);

        Assertions.assertEquals(customer, result);
        Mockito.verify(customerRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmailNonExistingCustomer() {
        String email = "john@example.com";

        Mockito.when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerDoesNotExistException.class, () -> {
            customerService.findByEmail(email);
        });
        Mockito.verify(customerRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    public void testUpsertCustomer() {
        Customer customer = Customer.builder().id(1L).email("old@example.com").name("john").build();

        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.upsertCustomer(customer);

        Assertions.assertEquals(customer, result);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(1L).email("old@example.com").name("john").build();

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(customerId);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(customerId);
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }
}