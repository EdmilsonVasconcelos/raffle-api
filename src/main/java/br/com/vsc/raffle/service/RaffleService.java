package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.RaffleDoesNotExistException;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import br.com.vsc.raffle.repository.RaffleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class RaffleService {

    private final RaffleRepository raffleRepository;

    private final NumberRaffleRepository numberRaffleRepository;

    public Page<Raffle> getAllRaffles(Pageable pageable) {
        return raffleRepository.findAll(pageable);
    }

    public Raffle getById(Long id) {
        return raffleRepository.findById(id)
                .orElseThrow(() -> new RaffleDoesNotExistException("Rifa não existe"));
    }

    public List<NumberRaffle> getNumbersByRaffle(Long id) {
        return numberRaffleRepository.getByRaffle(getById(id));
    }

    public Raffle saveRaffle(Raffle raffle) {
        return raffleRepository.save(raffle);
    }

    public List<NumberRaffle> createRaffleNumbers(Long raffleId) {
        Raffle raffle = getById(raffleId);

        Integer maximumNumbersRaffle = raffle.getMaximumNumbers();

        List<NumberRaffle> numbersRaffle = IntStream.range(0, maximumNumbersRaffle)
                .mapToObj(i -> NumberRaffle.builder()
                        .raffle(raffle)
                        .number((long) i)
                        .build())
                .collect(Collectors.toList());

        return numberRaffleRepository.saveAll(numbersRaffle);
    }

    public void deleteRaffle(Long id) {
        Raffle raffle = this.getById(id);
        raffleRepository.delete(raffle);
    }
}
