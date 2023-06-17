package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Raffle;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RaffleRepository extends PagingAndSortingRepository<Raffle, Long> {}