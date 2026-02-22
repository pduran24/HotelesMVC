package org.example.turismoapp.repository;

import org.example.turismoapp.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    Optional<Resena> findByHotel_IdAndCliente_Email(Long hotelId, String email);

}