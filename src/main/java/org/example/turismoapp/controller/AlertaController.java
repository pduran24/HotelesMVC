package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.service.AlertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;
    private final ClienteRepository clienteRepository;
    private final HotelRepository hotelRepository;

    @PostMapping("/crear")
    public String crearAlerta(
            @RequestParam Long hotelId,
            @RequestParam BigDecimal precioDeseado,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        Cliente cliente = clienteRepository.findByEmail(principal.getName()).orElse(null);
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if (cliente != null && hotel != null) {
            alertaService.crearAlerta(cliente, hotel, precioDeseado);

            redirectAttributes.addFlashAttribute("mensajeExito",
                    "¡Alerta creada! Te avisaremos si el precio baja a " + precioDeseado + "€ o menos.");
        }

        return "redirect:/hoteles/" + hotelId;
    }

    @GetMapping
    public String verMisAlertas(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        model.addAttribute("misAlertas", alertaService.obtenerMisAlertas(principal.getName()));
        return "avisos-mercado";
    }

    @PostMapping("/eliminar")
    public String eliminarAlerta(@RequestParam Long alertaId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            alertaService.eliminarAlerta(alertaId, principal.getName());
            redirectAttributes.addFlashAttribute("mensajeExito", "Alerta cancelada y eliminada.");
        }
        return "redirect:/alertas";
    }
}