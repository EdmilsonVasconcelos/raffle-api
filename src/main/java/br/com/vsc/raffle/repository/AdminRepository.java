package br.com.vsc.raffle.repository;

import java.util.Optional;

import br.com.vsc.raffle.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	Optional<Admin> findByEmail(String email);

}
