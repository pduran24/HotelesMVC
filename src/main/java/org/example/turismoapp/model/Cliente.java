package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidad que representa un cliente en el sistema.
 * Mapea a la tabla "clientes" en la base de datos.
 */
@Table(name = "clientes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Cliente {

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    /**
     * Nombre completo del cliente.
     */
    private String nombre;

    /**
     * Lista de reservas realizadas por este cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}
