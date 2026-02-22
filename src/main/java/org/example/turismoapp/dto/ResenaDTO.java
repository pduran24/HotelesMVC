package org.example.turismoapp.dto;

public record ResenaDTO(
        String nombreAutor,
        Integer puntuacion,
        String comentario,
        String fecha
) {
}