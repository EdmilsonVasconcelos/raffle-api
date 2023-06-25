package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.PaginatedListRaffleDTO;
import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import br.com.vsc.raffle.enums.RaffleStatus;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.service.ImageService;
import br.com.vsc.raffle.service.RaffleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RaffleControllerTest {

    @Mock
    private RaffleService raffleService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private RaffleController raffleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll_shouldReturnPaginatedListRaffleDTO() {
        Raffle raffle1 = Raffle.builder()
                .id(1L)
                .productName("raffle 1")
                .raffleStatus(RaffleStatus.WAITING)
                .build();

        Raffle raffle2 = Raffle.builder()
                .id(2L)
                .productName("raffle 2")
                .raffleStatus(RaffleStatus.WAITING)
                .build();

        List<Raffle> raffles = List.of(raffle1, raffle2);

        Page<Raffle> pageRaffles = new PageImpl<>(raffles);

        when(raffleService.getAllRaffles(any(Pageable.class))).thenReturn(pageRaffles);

        ResponseEntity<PaginatedListRaffleDTO> response = raffleController.getAll(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(raffles.size(), response.getBody().getRaffles().size());

        verify(raffleService, times(1)).getAllRaffles(PageRequest.of(0, 10));
    }

    @Test
    public void getById_shouldReturnRaffleDTO() {
        Raffle raffle = Raffle.builder()
                .id(1L)
                .productName("raffle 1")
                .raffleStatus(RaffleStatus.WAITING)
                .build();

        when(raffleService.getById(anyLong())).thenReturn(raffle);

        ResponseEntity<RaffleDTO> response = raffleController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(raffleService, times(1)).getById(1L);
    }

    @Test
    public void createRaffle_shouldReturnCreatedRaffleDTO() throws IOException {
        Raffle raffle = Raffle.builder()
                .id(1L)
                .productName("raffle 1")
                .raffleStatus(RaffleStatus.WAITING)
                .build();

        RaffleDTO raffleDTO = RaffleDTO.builder().productName("raffle 1").raffleStatus("WAITING").build();

        when(raffleService.saveRaffle(any(Raffle.class))).thenReturn(raffle);

        ResponseEntity<RaffleDTO> response = raffleController.createRaffle(raffleDTO, Collections.emptyList());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(raffleService, times(1)).saveRaffle(any(Raffle.class));
        verify(raffleService, times(1)).createRaffleNumbers(1L);
    }

    @Test
    public void delete_shouldReturnNoContent() {
        ResponseEntity<Void> response = raffleController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(raffleService, times(1)).deleteRaffle(1L);
    }
}