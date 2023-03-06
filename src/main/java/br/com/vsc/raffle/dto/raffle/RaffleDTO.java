package br.com.vsc.raffle.dto.raffle;

import br.com.vsc.raffle.dto.product.ProductDTO;
import br.com.vsc.raffle.model.Raffle;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RaffleDTO {

    private Long id;

    private Integer maximumNumbers;

    private String description;

    private BigDecimal price;

    private String status;

    private ProductDTO product;

    public static List<RaffleDTO> toList(List<Raffle> raffles) {
        return raffles.stream()
                .map(RaffleDTO::toDto)
                .collect(Collectors.toList());
    }

    public static RaffleDTO toDto(Raffle raffle) {
        return RaffleDTO.builder()
                .id(raffle.getId())
                .maximumNumbers(raffle.getMaximumNumbers())
                .description(raffle.getDescription())
                .price(raffle.getPrice())
                .product(ProductDTO.toDto(raffle.getProduct()))
                .status(raffle.getStatus().toString())
                .build();
    }
}
