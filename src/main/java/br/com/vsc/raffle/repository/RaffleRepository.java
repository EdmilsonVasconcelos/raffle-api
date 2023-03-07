package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleRepository extends JpaRepository<Raffle, Long> {}
