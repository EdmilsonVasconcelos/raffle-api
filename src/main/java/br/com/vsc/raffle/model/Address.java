package br.com.vsc.raffle.model;

import br.com.vsc.raffle.dto.address.AddressDTO;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String uf;

    public static Address toAddress(AddressDTO addressDTO) {
        return Address.builder()
                .city(addressDTO.getCity())
                .uf(addressDTO.getUf())
                .build();
    }
}
