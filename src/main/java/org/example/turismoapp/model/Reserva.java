package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una reserva en el sistema.
 * Mapea a la tabla "reservas" en la base de datos.
 */
@Table(name = "reservas")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Reserva {

    /**
     * Identificador único de la reserva.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    /**
     * Fecha de inicio de la reserva.
     */
    private LocalDate fechaEntrada;

    /**
     * Fecha de fin de la reserva.
     */
    private LocalDate fechaSalida;

    /**
     * Número total de días de la estancia.
     */
    private Integer numeroDias;

    /**
     * Precio total calculado de la reserva.
     */
    private BigDecimal precioTotal;

    /**
     * Hotel asociado a la reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /**
     * Cliente que realizó la reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
