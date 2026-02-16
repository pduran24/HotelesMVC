package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un usuario del sistema para autenticación y autorización.
 * Mapea a la tabla "users" en la base de datos.
 */
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para iniciar sesión.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Contraseña del usuario (debe almacenarse encriptada).
     */
    private String password;

    /**
     * Rol asignado al usuario (ej. ADMIN, USER).
     */
    private String role;
}
