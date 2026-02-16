package org.example.turismoapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para devolver la información de una reserva.
 *
 * @param id Identificador único de la reserva.
 * @param hotelNombre Nombre del hotel reservado.
 * @param clienteNombre Nombre del cliente que reservó.
 * @param fechaEntrada Fecha de inicio de la reserva.
 * @param fechaSalida Fecha de fin de la reserva.
 * @param numeroDias Número total de días de la reserva.
 * @param precioTotal Precio total de la reserva.
 */
public record ReservaResponse(
        Long id,
        String hotelNombre,
        String clienteNombre,
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        Integer numeroDias,
        BigDecimal precioTotal
) {
}
