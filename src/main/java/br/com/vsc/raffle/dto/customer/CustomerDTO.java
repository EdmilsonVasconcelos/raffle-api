package br.com.vsc.raffle.dto.customer;

import br.com.vsc.raffle.dto.address.AddressDTO;
import br.com.vsc.raffle.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CustomerDTO {

    private Long id;

    private String email;

    private String cpf;

    private String name;

    private AddressDTO address;

    private String phoneNumber;

    public static List<CustomerDTO> toList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerDTO::toDto)
                .collect(Collectors.toList());
    }

    public static CustomerDTO toDto(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .cpf(customer.getCpf())
                .name(customer.getName())
                .address(AddressDTO.toAddressDTO(customer.getAddress()))
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
