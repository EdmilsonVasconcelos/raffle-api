package br.com.vsc.raffle.dto.raffle;

import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaginatedListRaffleDTO {
    private long totalElements;
    private List<RaffleDTO> raffles;
}
