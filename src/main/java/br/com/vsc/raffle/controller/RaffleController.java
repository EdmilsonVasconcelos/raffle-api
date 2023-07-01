package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.raffle.PaginatedListRaffleDTO;
import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.service.ImageService;
import br.com.vsc.raffle.service.RaffleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    public ResponseEntity<PaginatedListRaffleDTO> getAll(
                                @PageableDefault(sort = "productName",
                                                direction = Sort.Direction.ASC,
                                                page = 0,
                                                size = 6) Pageable page) {

        Page<Raffle> raffles = raffleService.getAllRaffles(page);

        if(!raffles.hasContent()){
            return ResponseEntity.notFound().build();
        }

        List<RaffleDTO> rafflesDto = toList(raffles.getContent());

        PaginatedListRaffleDTO response = PaginatedListRaffleDTO.builder().totalElements(raffles.getTotalElements()).raffles(rafflesDto).build();

        return ResponseEntity.ok(response);
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

        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(raffleSaved));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        raffleService.deleteRaffle(id);
        return ResponseEntity.noContent().build();
    }

}
