package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.number.NumberRaffleDTO;
import br.com.vsc.raffle.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class NumberRaffle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    @ManyToOne
    @JoinColumn(name = "raffle_id")
    private Raffle raffle;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.AVAILABLE;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    public static NumberRaffle toDomain(NumberRaffleDTO numberRaffleDTO) {
        return NumberRaffle.builder()
                .customer(Customer.toDomain(numberRaffleDTO.getCustomer()))
                .paymentStatus(PaymentStatus.valueOf(numberRaffleDTO.getPaymentStatus()))
                .build();
    }

    public static List<NumberRaffle> toList(List<NumberRaffleDTO> numbers) {
        return numbers.stream()
                .map(NumberRaffle::toDomain)
                .collect(Collectors.toList());
    }
}
