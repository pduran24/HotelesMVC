package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una imagen asociada a un hotel.
 * Permite tener múltiples fotos por alojamiento (Relación N:1).
 */
@Entity
@Table(name = "hotel_imagenes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HotelImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * URL de la imagen (puede ser local o externa).
     */
    @Column(nullable = false)
    private String url;

    /**
     * Texto alternativo para accesibilidad (SEO y lectores de pantalla).
     */
    private String textoAlternativo;

    /**
     * Relación con el Hotel.
     * Muchos (Many) imágenes pertenecen a Un (One) Hotel.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @ToString.Exclude
    private Hotel hotel;
}