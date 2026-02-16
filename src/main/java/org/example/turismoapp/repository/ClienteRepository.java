package org.example.turismoapp.repository;

import org.example.turismoapp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Cliente.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
