package org.example.turismoapp.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la creación y actualización de clientes.
 *
 * @param nombre Nombre del cliente. Obligatorio.
 */
public record ClienteRequest(

        @NotBlank(message = "El nombre del cliente es obligatorio")
        String nombre
) {
}
