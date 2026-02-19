package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.ReservaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalController {
    private final ReservaService reservaService;

    @ModelAttribute("numReservas")
    public int cargarNumeroReservas(Authentication authentication) {

        if (authentication == null) {
            return 0;
        }

        String email = authentication.getName();

        return reservaService.obtenerReservasPorEmail(email).size();
    }
}
