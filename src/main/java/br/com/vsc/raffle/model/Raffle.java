package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.raffle.RaffleDTO;
import br.com.vsc.raffle.enums.RaffleStatus;
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
import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection
    private Set<String> images = new HashSet<>();

    private String productName;

    @Column(length = 20000)
    @Lob
    private String description;

    private BigDecimal price;

    private Integer maximumNumbers;

    @Enumerated(EnumType.STRING)
    private RaffleStatus raffleStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Raffle toDomain(RaffleDTO raffleDTO) {
        return Raffle.builder()
                .description(raffleDTO.getDescription())
                .maximumNumbers(raffleDTO.getMaximumNumbers())
                .productName(raffleDTO.getProductName())
                .price(raffleDTO.getPrice())
                .raffleStatus(RaffleStatus.valueOf(raffleDTO.getRaffleStatus()))
                .build();
    }
}
