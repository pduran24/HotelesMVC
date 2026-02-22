package org.example.turismoapp.dto;

public record ResenaDTO(
        String nombreAutor,
        String avatarAutor,
        Integer puntuacion,
        String comentario,
        String fecha
) {
}