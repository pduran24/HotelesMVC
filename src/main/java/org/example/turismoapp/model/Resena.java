package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "resenas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Puntuación de 1 a 5 estrellas.
     */
    @Column(nullable = false)
    private Integer puntuacion;

    /**
     * El texto de la opinión.
     */
    @Column(columnDefinition = "TEXT")
    private String comentario;

    /**
     * Fecha de publicación.
     */
    @Builder.Default
    private LocalDate fecha = LocalDate.now();

    /**
     * RELACIÓN N:1 con HOTEL
     * Muchas reseñas pertenecen a un solo hotel.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @ToString.Exclude
    private Hotel hotel;

    /**
     * RELACIÓN N:1 con CLIENTE
     * Muchas reseñas son escritas por un solo cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @ToString.Exclude
    private Cliente cliente;
}