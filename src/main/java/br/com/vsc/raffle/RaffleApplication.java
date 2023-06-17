package br.com.vsc.raffle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaAuditing
public class RaffleApplication {
	public static void main(String[] args) {
		SpringApplication.run(RaffleApplication.class, args);
	}
}
