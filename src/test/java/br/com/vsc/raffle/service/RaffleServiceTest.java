package br.com.vsc.raffle.service;

import br.com.vsc.raffle.enums.PaymentStatus;
import br.com.vsc.raffle.exception.RaffleDoesNotExistException;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.model.Raffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import br.com.vsc.raffle.repository.RaffleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RaffleServiceTest {

    @Mock
    private RaffleRepository raffleRepository;

    @Mock
    private NumberRaffleRepository numberRaffleRepository;

    @InjectMocks
    private RaffleService raffleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRaffles_shouldReturnAllRaffles() {
        Raffle raffle1 = Raffle.builder().id(1L).productName("name").build();
        Raffle raffle2 = Raffle.builder().id(2L).productName("name").build();

        List<Raffle> raffles = List.of(raffle1, raffle2);
        Page<Raffle> pageRaffles = new PageImpl<>(raffles);

        when(raffleRepository.findAll(any(Pageable.class))).thenReturn(pageRaffles);

        Page<Raffle> result = raffleService.getAllRaffles(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(raffles.size(), result.getTotalElements());
        assertEquals(raffles.size(), result.getContent().size());

        verify(raffleRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getById_existingRaffleId_shouldReturnRaffle() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(raffleId).productName("name").build();

        when(raffleRepository.findById(raffleId)).thenReturn(Optional.of(raffle));

        Raffle result = raffleService.getById(raffleId);

        assertNotNull(result);
        assertEquals(raffleId, result.getId());
        verify(raffleRepository, times(1)).findById(raffleId);
    }

    @Test
    void getById_nonExistingRaffleId_shouldThrowException() {
        Long raffleId = 1L;
        when(raffleRepository.findById(raffleId)).thenReturn(Optional.empty());

        assertThrows(RaffleDoesNotExistException.class, () -> raffleService.getById(raffleId));

        verify(raffleRepository, times(1)).findById(raffleId);
    }

    @Test
    void getNumbersByRaffle_existingRaffleId_shouldReturnNumbersRaffle() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(raffleId).productName("name").build();

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

        List<NumberRaffle> numbersRaffle = List.of(numberRaffle1, numberRaffle2);

        when(numberRaffleRepository.getByRaffle(any())).thenReturn(numbersRaffle);
        when(raffleRepository.findById(raffleId)).thenReturn(Optional.of(raffle));

        List<NumberRaffle> result = raffleService.getNumbersByRaffle(raffleId);

        assertEquals(numbersRaffle.size(), result.size());
        assertEquals(numbersRaffle.get(0).getId(), result.get(0).getId());
        assertEquals(numbersRaffle.get(1).getId(), result.get(1).getId());

        verify(numberRaffleRepository, times(1)).getByRaffle(raffle);
    }

    @Test
    void saveRaffle_validRaffle_shouldReturnSavedRaffle() {
        Raffle raffleToSave = Raffle.builder().productName("some name").build();
        Raffle savedRaffle = Raffle.builder().id(1L).productName("some name").build();

        when(raffleRepository.save(raffleToSave)).thenReturn(savedRaffle);

        Raffle result = raffleService.saveRaffle(raffleToSave);

        assertNotNull(result);
        assertEquals(savedRaffle.getId(), result.getId());
        verify(raffleRepository, times(1)).save(raffleToSave);
    }

    @Test
    void createRaffleNumbers_existingRaffleId_shouldReturnCreatedNumbersRaffle() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(1L).productName("Raffle 1").maximumNumbers(100).build();

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

        List<NumberRaffle> numbersRaffle = List.of(numberRaffle1, numberRaffle2);

        when(raffleRepository.findById(raffleId)).thenReturn(Optional.of(raffle));
        when(numberRaffleRepository.saveAll(any())).thenReturn(numbersRaffle);

        List<NumberRaffle> result = raffleService.createRaffleNumbers(raffleId);

        assertNotNull(result);
        assertEquals(numbersRaffle.size(), result.size());
        assertEquals(numbersRaffle.get(0).getId(), result.get(0).getId());
        assertEquals(numbersRaffle.get(1).getId(), result.get(1).getId());
        assertEquals(result.size(), 2);

        verify(numberRaffleRepository, times(1)).saveAll(any());
    }

    @Test
    void deleteRaffle_existingRaffleId_shouldDeleteRaffleAndRelatedNumberRaffles() {
        Long raffleId = 1L;
        Raffle raffle = Raffle.builder().id(1L).productName("Raffle 1").build();

        when(raffleRepository.findById(anyLong())).thenReturn(Optional.of(raffle));

        raffleService.deleteRaffle(raffleId);

        verify(raffleRepository, times(1)).delete(raffle);
    }
}
