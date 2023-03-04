package br.com.vsc.raffle.dto.product;

import br.com.vsc.raffle.model.Product;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

	private Long id;
	
	@NotNull(message = "A descrição é obrigatória")
	@Size(message = "A descrição deve ter entre 2 e 50 caracteres", min = 2, max = 90)
	private String description;
	
	@NotNull(message = "O preco é obrigatório")
	private BigDecimal price;
	
	private String status;

	public static ProductDTO toDto(Product product) {
		return ProductDTO.builder()
				.description(product.getDescription())
				.id(product.getId())
				.price(product.getPrice())
				.status(product.getStatus().toString())
				.build();
	}

	public static List<ProductDTO> toList(List<Product> products) {
		return products.stream()
				.map(product -> ProductDTO.builder()
						.description(product.getDescription())
						.id(product.getId())
						.price(product.getPrice())
						.status(product.getStatus().toString())
						.build())
				.collect(Collectors.toList());
	}
}