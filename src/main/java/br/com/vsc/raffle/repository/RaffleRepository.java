package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaffleRepository extends JpaRepository<Raffle, Long> {

    Optional<Raffle> findByDescription(String description);

}
