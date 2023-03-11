package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.service.ImageService;
import br.com.vsc.raffle.service.RaffleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
    import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.vsc.raffle.dto.raffle.RaffleDTO.toDto;
import static br.com.vsc.raffle.dto.raffle.RaffleDTO.toList;

@AllArgsConstructor
@RestController
@RequestMapping("/raffle")
public class RaffleController {

    private final RaffleService raffleService;

    private final ImageService imageService;

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
    public ResponseEntity<RaffleDTO> createRaffle(@Valid RaffleDTO raffleDTO, @RequestPart List<MultipartFile> images) throws IOException {
        Set<String> newImages = new HashSet<>();

        for (MultipartFile image : images) {
            String imageSaved = imageService.saveImage(image);
            newImages.add(imageSaved);
        }

        Raffle raffle = Raffle.toDomain(raffleDTO);

        raffle.setImages(newImages);

        Raffle raffleSaved = raffleService.saveRaffle(raffle);

        raffleService.createRaffleNumbers(raffleSaved.getId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(raffleSaved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(toDto(raffleSaved));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        raffleService.deleteRaffle(id);
        return ResponseEntity.noContent().build();
    }

}
