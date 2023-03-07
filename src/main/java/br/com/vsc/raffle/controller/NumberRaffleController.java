package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.number.NumberRaffleDTO;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.service.NumberRaffleService;
import br.com.vsc.raffle.service.RaffleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.vsc.raffle.dto.number.NumberRaffleDTO.toDto;
import static br.com.vsc.raffle.dto.number.NumberRaffleDTO.toList;

@AllArgsConstructor
@RestController
@RequestMapping("/number")
public class NumberRaffleController {

    private final NumberRaffleService numberRaffleService;

    private final RaffleService raffleService;

    @GetMapping
    public ResponseEntity<List<NumberRaffleDTO>> getAll() {
        List<NumberRaffle> numberRaffles = numberRaffleService.getAll();
        return ResponseEntity.ok(toList(numberRaffles));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NumberRaffleDTO> getById(@PathVariable Long id) {
        NumberRaffle numberRaffle = numberRaffleService.getById(id);
        return ResponseEntity.ok(toDto(numberRaffle));
    }

    @GetMapping(value = "/by-raffle")
    public ResponseEntity<List<NumberRaffleDTO>> getByRaffle(@RequestParam Long raffleId) {
        List<NumberRaffle> numberRaffles = raffleService.getNumbersByRaffle(raffleId);
        return ResponseEntity.ok(toList(numberRaffles));
    }

    @PostMapping
    public ResponseEntity<List<NumberRaffleDTO>> save(@RequestParam Long raffleId) {
        List<NumberRaffle> numberRaffle = raffleService.createRaffleNumbers(raffleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toList(numberRaffle));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        numberRaffleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
