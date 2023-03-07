package br.com.vsc.raffle.dto.raffle;

import br.com.vsc.raffle.model.Raffle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleDTO {

    private Long id;

    private Integer maximumNumbers;

    @NotNull(message = "A descrição é obrigatória")
    @Size(min = 1, max = 20000, message = "A descrição deve ter entre 1 e 20.000 caracteres")
    private String description;

    private BigDecimal price;

    private String status;

    private String productName;

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
                .build();
    }
}
