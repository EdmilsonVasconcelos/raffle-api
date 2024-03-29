package br.com.vsc.raffle.dto.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class AuthRequestDTO {
	
	@NotNull(message = "The email of admin is mandatory")
	@Size(message = "The email of admin must be between two and fifty characters", min = 2, max = 50)
	private String email;
	
	@NotNull(message = "The password of admin is mandatory")
	@Size(message = "The password of admin must be between two and fifty characters", min = 2, max = 1000)
	private String password;
	
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
