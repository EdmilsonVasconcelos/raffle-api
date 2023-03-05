package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.RaffleDoesNotExistException;
import br.com.vsc.raffle.model.Product;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.ProductRepository;
import br.com.vsc.raffle.repository.RaffleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RaffleService {

    private final RaffleRepository raffleRepository;

    private final ProductRepository productRepository;

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

    public Raffle saveRaffle(Raffle raffle, Long productId) {
        Product product = productRepository.getById(productId);
        raffle.setProduct(product);
        return raffleRepository.save(raffle);
    }

    public void deleteRaffle(Long id) {
        Raffle raffle = this.getById(id);
        raffleRepository.delete(raffle);
    }

}
