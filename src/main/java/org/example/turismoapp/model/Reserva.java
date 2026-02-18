package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.turismoapp.model.enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Entidad que representa una reserva en el sistema.
 * Mapea a la tabla "reservas" en la base de datos.
 */
@Table(name = "reservas")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
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
     * Precio total calculado de la reserva.
     */
    private BigDecimal precioTotal;

    /**
     * Estado de la reserva.
     * Por defecto se inicializa en CONFIRMADA.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoReserva estado = EstadoReserva.CONFIRMADA;

    /**
     * Fecha de auditoría para saber cuándo se hizo la reserva.
     */
    @Builder.Default
    private LocalDate fechaCreacion = LocalDate.now();

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

    /**
     * Calcula el número de noches dinámicamente.
     */
    public long getNumeroNoches() {
        if (fechaEntrada == null || fechaSalida == null) return 0;
        return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }

}
