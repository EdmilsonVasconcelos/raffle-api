package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.auth.AuthRequestDTO;
import br.com.vsc.raffle.dto.auth.TokenDTO;
import br.com.vsc.raffle.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> authenticate(@Valid AuthRequestDTO request) {
		UsernamePasswordAuthenticationToken dataLogin = request.converter();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(dataLogin);
			
			String token = tokenService.generateToken(authentication);
			
			return ResponseEntity.ok(new TokenDTO("Bearer", token));
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().build();
			
		}
	}
}
