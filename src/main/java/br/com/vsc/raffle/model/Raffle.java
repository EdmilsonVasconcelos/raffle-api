package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Raffle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maximumNumbers;

    @Column(length = 10000)
    @Lob
    private String description;

    private BigDecimal price;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    public static Raffle toDomain(RaffleDTO raffleDTO) {
        return Raffle.builder()
                .description(raffleDTO.getDescription())
                .maximumNumbers(raffleDTO.getMaximumNumbers())
                .price(raffleDTO.getPrice())
                .build();
    }
}
