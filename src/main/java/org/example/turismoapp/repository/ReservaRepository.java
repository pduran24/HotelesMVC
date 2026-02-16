package org.example.turismoapp.repository;

import org.example.turismoapp.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Reserva.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
