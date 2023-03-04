package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
