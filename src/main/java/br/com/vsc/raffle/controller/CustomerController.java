package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.customer.CustomerDTO;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(CustomerDTO.toList(customers));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(CustomerDTO.toDto(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO request) {
        Customer customer = customerService.upsertCustomer(Customer.toDomain(request));

        return new ResponseEntity<>(CustomerDTO.toDto(customer), CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
