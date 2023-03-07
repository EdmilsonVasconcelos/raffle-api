package br.com.vsc.raffle.dto.raffle;

import br.com.vsc.raffle.enums.RaffleStatus;
import br.com.vsc.raffle.model.Raffle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleDTO {

    private Long id;

    @Min(value = 10, message = "A quantidade mínima de números para a rifa é 10")
    @Max(value = 100000, message = "A quantidade máxima de números para a rifa é 100000")
    private Integer maximumNumbers;

    @NotEmpty(message = "O nome do produto é obrigatório")
    @Size(min = 1, max = 50, message = "O produto deve ter entre 1 e 50 caracteres")
    private String productName;

    @NotEmpty(message = "A descrição é obrigatória")
    @Size(min = 1, max = 20000, message = "A descrição deve ter entre 1 e 20.000 caracteres")
    private String description;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @DecimalMax(value = "999999.99", message = "O preço deve ser menor que 999999.99")
    private BigDecimal price;

    private String raffleStatus = RaffleStatus.WAITING.toString();

    public static List<RaffleDTO> toList(List<Raffle> raffles) {
        return raffles.stream()
                .map(RaffleDTO::toDto)
                .collect(Collectors.toList());
    }

    public static RaffleDTO toDto(Raffle raffle) {
        return RaffleDTO.builder()
                .id(raffle.getId())
                .maximumNumbers(raffle.getMaximumNumbers())
                .productName(raffle.getProductName())
                .description(raffle.getDescription())
                .price(raffle.getPrice())
                .raffleStatus(raffle.getRaffleStatus().toString())
                .build();
    }
}
