package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.NumberRaffleDoesNotExistException;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public NumberRaffle save(NumberRaffle numberRaffle, Long idRaffle) {
        Raffle raffle = raffleService.getById(idRaffle);
        numberRaffle.setRaffle(raffle);
        return numberRaffleRepository.save(numberRaffle);
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
