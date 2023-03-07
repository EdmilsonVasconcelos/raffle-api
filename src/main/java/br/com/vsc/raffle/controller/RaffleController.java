package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.service.RaffleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static br.com.vsc.raffle.dto.raffle.RaffleDTO.toDto;
import static br.com.vsc.raffle.dto.raffle.RaffleDTO.toList;

@AllArgsConstructor
@RestController
@RequestMapping("/raffle")
public class RaffleController {

    private final RaffleService raffleService;

    @GetMapping
    public ResponseEntity<List<RaffleDTO>> getAll() {
        List<Raffle> raffles = raffleService.getAllRaffles();
        return ResponseEntity.ok(toList(raffles));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RaffleDTO> getById(@PathVariable Long id) {
        Raffle raffle = raffleService.getById(id);
        return ResponseEntity.ok(toDto(raffle));
    }

    @PostMapping
    public ResponseEntity<RaffleDTO> save(@Valid RaffleDTO raffleDTO,
                                                @RequestParam Long productId) {
        Raffle raffle = raffleService.saveRaffle(Raffle.toDomain(raffleDTO), productId);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(raffle.getId())
                .toUri();

        return ResponseEntity.created(uri).body(toDto(raffle));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        raffleService.deleteRaffle(id);
        return ResponseEntity.noContent().build();
    }

}
