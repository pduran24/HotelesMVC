package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * Email del cliente, debe ser único y no nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Lista de reservas realizadas por este cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    /**
     * Historial de reseñas escritas por este cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> misResenas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cliente_favoritos",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @ToString.Exclude
    private Set<Hotel> favoritos = new HashSet<>();
}
