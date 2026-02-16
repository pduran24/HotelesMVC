package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad que representa un hotel en el sistema.
 * Mapea a la tabla "hoteles" en la base de datos.
 */
@Table(name = "hoteles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Hotel {

    /**
     * Identificador único del hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del hotel.
     */
    private String nombre;

    /**
     * Ubicación geográfica o dirección del hotel.
     */
    private String ubicacion;

    /**
     * Descripción detallada del hotel.
     */
    private String descripcion;

    /**
     * Clasificación del hotel en estrellas (1-5).
     */
    private Integer estrellas;

    /**
     * Precio por noche en el hotel.
     */
    private BigDecimal precioNoche;

    /**
     * Lista de reservas asociadas a este hotel.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}
