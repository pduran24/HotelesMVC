package org.example.turismoapp.dto;

/**
 * DTO para devolver información de errores en la API.
 *
 * @param error Tipo de error.
 * @param message Mensaje descriptivo del error.
 * @param status Código de estado HTTP.
 */
public record ErrorResponseDTO(
        String error,
        String message,
        Integer status
) {
}
