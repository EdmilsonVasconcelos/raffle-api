package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.NumberRaffle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<NumberRaffle, Long> {}
