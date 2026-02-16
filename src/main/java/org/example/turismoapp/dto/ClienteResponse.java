package org.example.turismoapp.dto;

/**
 * DTO para devolver la información de un cliente.
 *
 * @param id Identificador único del cliente.
 * @param nombre Nombre del cliente.
 */
public record ClienteResponse(
        Long id,
        String nombre
) {
}
