package br.com.vsc.raffle.dto.address;

import br.com.vsc.raffle.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressDTOTest {

    @Test
    public void toAddressDTO() {
        Address address = Address.builder().city("São Paulo").uf("SP").build();
        AddressDTO addressDTO = AddressDTO.toAddressDTO(address);

        assertEquals("São Paulo", addressDTO.getCity());
        assertEquals("SP", addressDTO.getUf());
    }

}