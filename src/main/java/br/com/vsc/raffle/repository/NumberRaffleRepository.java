package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NumberRaffleRepository extends JpaRepository<NumberRaffle, Long> {

    List<NumberRaffle> getByRaffle(Raffle raffle);

}
