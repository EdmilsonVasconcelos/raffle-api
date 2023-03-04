package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
