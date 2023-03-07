package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.RaffleDoesNotExistException;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import br.com.vsc.raffle.repository.RaffleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RaffleService {

    private final RaffleRepository raffleRepository;

    private final NumberRaffleRepository numberRaffleRepository;

    public List<Raffle> getAllRaffles() {
        return raffleRepository.findAll();
    }

    public Raffle getById(Long id) {
        return raffleRepository.findById(id)
                .orElseThrow(() -> new RaffleDoesNotExistException("Rifa não existe"));
    }

    public Raffle getByDescription(String description) {
        return raffleRepository.findByDescription(description)
                .orElseThrow(() -> new RaffleDoesNotExistException("Rifa não existe"));
    }

    public List<NumberRaffle> getNumbersByRaffle(Long id) {
        return numberRaffleRepository.getByRaffle(getById(id));
    }

    public Raffle saveRaffle(Raffle raffle) {
        Raffle raffleSaved = raffleRepository.save(raffle);

        saveNumbers(raffleSaved.getId());

        return raffleSaved;
    }

    public List<NumberRaffle> saveNumbers(Long raffleId) {
        Raffle raffle = getById(raffleId);

        Integer maximumNumbersRaffle = raffle.getMaximumNumbers();
        List<NumberRaffle> numbersRaffle = new ArrayList<>();

        for (int i = 0; i < maximumNumbersRaffle; i++) {
            NumberRaffle numberRaffle = new NumberRaffle();
            numberRaffle.setRaffle(raffle);
            numberRaffle.setNumber((long) i);

            numbersRaffle.add(numberRaffle);
        }

        return numberRaffleRepository.saveAll(numbersRaffle);
    }

    public void deleteRaffle(Long id) {
        Raffle raffle = this.getById(id);
        raffleRepository.delete(raffle);
    }
}
