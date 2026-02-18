package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ReservaResponse;
import org.example.turismoapp.service.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mis-reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public String listarMisReservas(Model model, Principal principal) {
        // 1. Obtenemos el email del usuario logueado
        String email = principal.getName();

        // 2. Pedimos sus reservas al servicio
        List<ReservaResponse> misReservas = reservaService.obtenerReservasPorEmail(email);

        // 3. Pasamos la lista a la vista HTML
        model.addAttribute("reservas", misReservas);

        return "mis-reservas"; // Nombre del archivo HTML
    }
}