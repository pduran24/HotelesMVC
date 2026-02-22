package org.example.turismoapp.dto;

import java.time.LocalDate;

public record PronosticoDiarioDTO(
        LocalDate fecha,
        Double tempMax,
        Double tempMin,
        String descripcion,
        String iconoCss
) {}