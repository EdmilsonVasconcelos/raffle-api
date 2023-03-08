package br.com.vsc.raffle.repository;

import br.com.vsc.raffle.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
