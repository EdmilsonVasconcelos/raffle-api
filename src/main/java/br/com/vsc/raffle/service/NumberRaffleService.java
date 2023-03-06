package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.NumberRaffleDoesNotExistException;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class NumberRaffleService {

    private final NumberRaffleRepository numberRaffleRepository;

    private final RaffleService raffleService;

    private final CustomerService customerService;

    public List<NumberRaffle> getAll() {
        return numberRaffleRepository.findAll();
    }

    public NumberRaffle getById(Long id) {
        return numberRaffleRepository.findById(id)
                .orElseThrow(() ->  new NumberRaffleDoesNotExistException("Número de sorteio não exite"));
    }
    public List<NumberRaffle> getByRaffle(Long id) {
        return numberRaffleRepository.getByRaffle(raffleService.getById(id));
    }

    public List<NumberRaffle> saveNumbers(Long raffleId) {
        Raffle raffle = raffleService.getById(raffleId);

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

    public NumberRaffle update(NumberRaffle numberRaffle, Long customerId) {
        Customer customer = customerService.findById(customerId);
        numberRaffle.setCustomer(customer);
        return numberRaffleRepository.save(numberRaffle);
    }

    public void delete(Long id) {
        NumberRaffle numberRaffle = this.getById(id);
        numberRaffleRepository.delete(numberRaffle);
    }
}
