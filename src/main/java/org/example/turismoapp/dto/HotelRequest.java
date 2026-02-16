package org.example.turismoapp.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO para la creación y actualización de hoteles.
 * Contiene las validaciones necesarias para los datos de entrada.
 *
 * @param nombre Nombre del hotel. Obligatorio.
 * @param ubicacion Ubicación del hotel. Obligatoria.
 * @param descripcion Descripción opcional del hotel.
 * @param estrellas Clasificación del hotel (1-5). Obligatorio.
 * @param precioNoche Precio por noche. Obligatorio y mayor a 10.
 */
public record HotelRequest(

        @NotBlank(message = "El nombre del hotel es obligatorio")
        String nombre,

        @NotBlank(message = "La ubicación del hotel es obligatoria")
        String ubicacion,

        String descripcion,

        @NotNull(message = "La cantidad de estrellas es obligatoria")
        @Min(1) @Max(5)
        Integer estrellas,

        @NotNull(message = "El precio por noche es obligatorio")
        @DecimalMin(value = "10.0", inclusive = false, message = "El precio por noche debe ser mayor a 10")
        BigDecimal precioNoche
) {
}
