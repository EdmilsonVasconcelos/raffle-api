package br.com.vsc.raffle.dto.address;

import br.com.vsc.raffle.model.Address;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class AddressDTO {
    @Size(min = 2, max = 255, message = "A cidade deve ter entre 2 e 255 caracteres")
    @NotNull(message = "A cidade é obrigatória")
    private String city;

    @Size(min = 2, max = 255, message = "A UF deve ter entre 2 e 255 caracteres")
    @NotNull(message = "A UF da casa é obrigatória")
    private String uf;

    public static AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .city(address.getCity())
                .uf(address.getUf())
                .build();
    }
}
