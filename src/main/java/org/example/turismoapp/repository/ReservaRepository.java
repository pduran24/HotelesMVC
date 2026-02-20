package org.example.turismoapp.repository;

import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Reserva.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente(Cliente cliente);
    long countByCliente_Email(String email);
}
