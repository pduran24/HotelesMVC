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
     * Latitud geográfica.
     */
    private Double latitud;

    /**
     * Longitud geográfica.
     */
    private Double longitud;

    /**
     * Lista de reservas asociadas a este hotel.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    /**
     * Lista de imágenes asociadas a este hotel.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelImagen> imagenes;

    /**
     * Lista de reseñas asociadas a este hotel.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;

    /**
     * Calcula la media de estrellas al vuelo.
     * @return Valor entre 0.0 y 5.0
     */
    public Double getPuntuacionMedia() {
        if (resenas == null || resenas.isEmpty()) {
            return 0.0;
        }
        double suma = resenas.stream()
                .mapToInt(Resena::getPuntuacion)
                .sum();
        return Math.round((suma / resenas.size()) * 10.0) / 10.0; // Redondeo a 1 decimal
    }

    /**
     * Devuelve el número total de opiniones
     */
    public int getNumeroResenas() {
        return resenas != null ? resenas.size() : 0;
    }
}
