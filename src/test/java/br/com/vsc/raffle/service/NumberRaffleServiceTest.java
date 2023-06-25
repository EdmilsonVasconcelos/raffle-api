package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.NumberRaffleDoesNotExistException;
import br.com.vsc.raffle.model.Customer;
import br.com.vsc.raffle.model.NumberRaffle;
import br.com.vsc.raffle.repository.NumberRaffleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NumberRaffleServiceTest {

    @Mock
    private NumberRaffleRepository numberRaffleRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private NumberRaffleService numberRaffleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        NumberRaffle numberRaffle1 = new NumberRaffle();
        NumberRaffle numberRaffle2 = new NumberRaffle();
        List<NumberRaffle> numberRaffles = Arrays.asList(numberRaffle1, numberRaffle2);

        Mockito.when(numberRaffleRepository.findAll()).thenReturn(numberRaffles);

        List<NumberRaffle> result = numberRaffleService.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(numberRaffle1));
        Assertions.assertTrue(result.contains(numberRaffle2));
    }

    @Test
    public void testGetByIdExistingNumberRaffle() {
        Long numberRaffleId = 1L;
        NumberRaffle numberRaffle = new NumberRaffle();
        Mockito.when(numberRaffleRepository.findById(numberRaffleId)).thenReturn(Optional.of(numberRaffle));

        NumberRaffle result = numberRaffleService.getById(numberRaffleId);

        Assertions.assertEquals(numberRaffle, result);
    }

    @Test
    public void testGetByIdNonExistingNumberRaffle() {
        Long numberRaffleId = 1L;
        Mockito.when(numberRaffleRepository.findById(numberRaffleId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NumberRaffleDoesNotExistException.class, () -> {
            numberRaffleService.getById(numberRaffleId);
        });
    }

    @Test
    public void testUpdateNumberRaffle() {
        Long numberRaffleId = 1L;
        Long customerId = 2L;
        NumberRaffle numberRaffle = new NumberRaffle();
        Customer customer = new Customer();
        Mockito.when(numberRaffleRepository.findById(numberRaffleId)).thenReturn(Optional.of(numberRaffle));
        Mockito.when(customerService.findById(customerId)).thenReturn(customer);
        Mockito.when(numberRaffleRepository.save(numberRaffle)).thenReturn(numberRaffle);

        NumberRaffle result = numberRaffleService.update(numberRaffle, customerId);

        Assertions.assertEquals(customer, numberRaffle.getCustomer());
        Assertions.assertEquals(numberRaffle, result);
    }

    @Test
    public void testDeleteNumberRaffle() {
        Long numberRaffleId = 1L;
        NumberRaffle numberRaffle = new NumberRaffle();
        Mockito.when(numberRaffleRepository.findById(numberRaffleId)).thenReturn(Optional.of(numberRaffle));

        numberRaffleService.delete(numberRaffleId);

        Mockito.verify(numberRaffleRepository, Mockito.times(1)).delete(numberRaffle);
    }
}
