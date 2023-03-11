package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.customer.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String cpf;

	private String name;

	@Embedded
	private Address address;

	private String phoneNumber;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Customer toDomain(CustomerDTO customerDTO) {
		return Customer.builder()
				.id(customerDTO.getId())
				.email(customerDTO.getEmail())
				.cpf(customerDTO.getCpf())
				.name(customerDTO.getName())
				.address(Address.toAddress(customerDTO.getAddress()))
				.phoneNumber(customerDTO.getPhoneNumber())
				.build();
	}
}
