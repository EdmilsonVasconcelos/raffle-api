package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.number.NumberRaffleDTO;
import br.com.vsc.raffle.enums.PaymentStatus;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.service.NumberRaffleService;
import br.com.vsc.raffle.service.RaffleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class NumberRaffleControllerTest {

    @Mock
    private NumberRaffleService numberRaffleService;

    @Mock
    private RaffleService raffleService;

    @InjectMocks
    private NumberRaffleController numberRaffleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll_shouldReturnWithSuccess() {
        Raffle raffle = Raffle.builder().id(1L).productName("name").build();

        NumberRaffle numberRaffle1 = NumberRaffle.builder()
                .id(1L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        NumberRaffle numberRaffle2 = NumberRaffle.builder()
                .id(2L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        List<NumberRaffle> numberRaffles = Arrays.asList(numberRaffle1, numberRaffle2);

        Mockito.when(numberRaffleService.getAll()).thenReturn(numberRaffles);

        ResponseEntity<List<NumberRaffleDTO>> response = numberRaffleController.getAll();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    public void getById_shouldReturnWithSuccess() {
        Long numberRaffleId = 1L;
        Raffle raffle = Raffle.builder().id(numberRaffleId).productName("name").build();

        NumberRaffle numberRaffle = NumberRaffle.builder()
                .id(1L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        Mockito.when(numberRaffleService.getById(numberRaffleId)).thenReturn(numberRaffle);

        ResponseEntity<NumberRaffleDTO> response = numberRaffleController.getById(numberRaffleId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getByRaffle_shouldReturnWithSuccess() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(1L).productName("name").build();

        NumberRaffle numberRaffle1 = NumberRaffle.builder()
                .id(1L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        NumberRaffle numberRaffle2 = NumberRaffle.builder()
                .id(2L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        List<NumberRaffle> numberRaffles = Arrays.asList(numberRaffle1, numberRaffle2);
        Mockito.when(raffleService.getNumbersByRaffle(raffleId)).thenReturn(numberRaffles);

        ResponseEntity<List<NumberRaffleDTO>> response = numberRaffleController.getByRaffle(raffleId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    public void save_shouldSaveWithSuccess() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(1L).productName("name").build();

        NumberRaffle numberRaffle1 = NumberRaffle.builder()
                .id(1L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        NumberRaffle numberRaffle2 = NumberRaffle.builder()
                .id(2L)
                .paymentStatus(PaymentStatus.PAID)
                .raffle(raffle)
                .build();

        List<NumberRaffle> numberRaffles = Arrays.asList(numberRaffle1, numberRaffle2);
        Mockito.when(raffleService.createRaffleNumbers(raffleId)).thenReturn(numberRaffles);

        ResponseEntity<List<NumberRaffleDTO>> response = numberRaffleController.save(raffleId);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    public void delete_shouldDeleteWithSuccess() {
        Long numberRaffleId = 1L;

        ResponseEntity<Void> response = numberRaffleController.delete(numberRaffleId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(numberRaffleService, Mockito.times(1)).delete(numberRaffleId);
    }
}
