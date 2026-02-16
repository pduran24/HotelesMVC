package org.example.turismoapp.repository;

import org.example.turismoapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Hotel.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
