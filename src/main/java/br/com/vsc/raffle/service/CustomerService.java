package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.CustomerDoesNotExistException;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerDoesNotExistException("Cliente não existe"));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerDoesNotExistException("Cliente não existe"));
    }

    public Customer upsertCostumer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCostumer(Long id) {
        Customer costumer = findById(id);
        customerRepository.delete(costumer);
    }
}
