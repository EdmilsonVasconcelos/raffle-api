package br.com.vsc.raffle.dto.number;

import br.com.vsc.raffle.dto.customer.CustomerDTO;
import br.com.vsc.raffle.model.NumberRaffle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NumberRaffleDTO {

    private Long id;

    private Long number;

    private CustomerDTO customer;

    private String paymentStatus;

    private Long raffleId;

    private String productDescription;

    public static List<NumberRaffleDTO> toList(List<NumberRaffle> numberRaffles) {
        return numberRaffles.stream()
                .map(NumberRaffleDTO::toDto)
                .collect(Collectors.toList());
    }

    public static NumberRaffleDTO toDto(NumberRaffle numberRaffle) {
        return NumberRaffleDTO.builder()
                .id(numberRaffle.getId())
                .number(numberRaffle.getNumber())
                .customer(numberRaffle.getCustomer() != null ? CustomerDTO.toDto(numberRaffle.getCustomer()) : null)
                .paymentStatus(numberRaffle.getPaymentStatus().toString())
                .raffleId(numberRaffle.getRaffle().getId())
                .build();
    }
}
