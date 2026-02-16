package org.example.turismoapp.repository;

import org.example.turismoapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad UserEntity.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<UserEntity> findByUsername(String username);
}
