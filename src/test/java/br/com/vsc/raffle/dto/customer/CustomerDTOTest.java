package br.com.vsc.raffle.dto.customer;

import br.com.vsc.raffle.model.Address;
import br.com.vsc.raffle.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerDTOTest {

    @Test
    public void toCustomerDTO() {
        Address address = Address.builder()
                .city("Sao Paulo")
                .uf("SP")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .email("john@example.com")
                .name("john")
                .cpf("xxx-xxx-xxx-xx")
                .address(address)
                .phoneNumber("12-45678910")
                .build();

        CustomerDTO customerDTO = CustomerDTO.toDto(customer);

        assertEquals("Sao Paulo", customerDTO.getAddress().getCity());
        assertEquals("SP", customerDTO.getAddress().getUf());
        assertEquals("john@example.com", customerDTO.getEmail());
        assertEquals("john", customerDTO.getName());
        assertEquals("xxx-xxx-xxx-xx", customerDTO.getCpf());
        assertEquals("12-45678910", customerDTO.getPhoneNumber());
        assertEquals(1L, customerDTO.getId());
    }

    @Test
    public void toListDTO() {
        Address address = Address.builder()
                .city("Sao Paulo")
                .uf("SP")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .email("john@example.com")
                .name("john")
                .cpf("xxx-xxx-xxx-xx")
                .address(address)
                .phoneNumber("12-45678910")
                .build();

        List<CustomerDTO> customersDTO = CustomerDTO.toList(List.of(customer));

        assertEquals(1, customersDTO.size());
        assertEquals("Sao Paulo", customersDTO.get(0).getAddress().getCity());
        assertEquals("SP", customersDTO.get(0).getAddress().getUf());
        assertEquals("john@example.com", customersDTO.get(0).getEmail());
        assertEquals("john", customersDTO.get(0).getName());
        assertEquals("xxx-xxx-xxx-xx", customersDTO.get(0).getCpf());
        assertEquals("12-45678910", customersDTO.get(0).getPhoneNumber());
        assertEquals(1L, customersDTO.get(0).getId());
    }
}