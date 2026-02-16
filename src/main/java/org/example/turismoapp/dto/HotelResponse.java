package org.example.turismoapp.dto;

import java.math.BigDecimal;

/**
 * DTO para devolver la información de un hotel.
 *
 * @param id Identificador único del hotel.
 * @param nombre Nombre del hotel.
 * @param ubicacion Ubicación del hotel.
 * @param descripcion Descripción del hotel.
 * @param estrellas Clasificación del hotel.
 * @param precioNoche Precio por noche.
 */
public record HotelResponse(
        Long id,
        String nombre,
        String ubicacion,
        String descripcion,
        Integer estrellas,
        BigDecimal precioNoche
) {
}
