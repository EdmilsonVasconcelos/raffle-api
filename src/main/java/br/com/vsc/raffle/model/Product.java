package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.product.ProductDTO;
import br.com.vsc.raffle.enums.StatusProduct;
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
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private BigDecimal price;
	
	private String description;

	@Enumerated(EnumType.STRING)
	private StatusProduct status;

    @CreatedDate
    @Column(updatable = false)
	private LocalDateTime created;
		
    @LastModifiedDate
	private LocalDateTime updated;

    public static Product toDomain(ProductDTO productDTO) {
    	return Product.builder()
				.id(productDTO.getId())
				.price(productDTO.getPrice())
				.description(productDTO.getDescription())
				.status(StatusProduct.valueOf(productDTO.getStatus()))
				.build();
	}
}
