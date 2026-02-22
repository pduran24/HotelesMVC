package org.example.turismoapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la creación y actualización de clientes.
 *
 * @param nombre Nombre del cliente. Obligatorio.
 */
public record ClienteRequest(

        @NotBlank(message = "El nombre del cliente es obligatorio")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un email válido")
        String email,
        String biografia,
        String telefono
) {
}
