package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ReservaRequest;
import org.example.turismoapp.dto.ReservaResponse;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Reserva;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.service.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final ClienteRepository clienteRepository;

    @GetMapping("/mis-reservas")
    public String listarMisReservas(Model model, Principal principal) {
        String email = principal.getName();
        List<ReservaResponse> misReservas = reservaService.obtenerReservasPorEmail(email);
        model.addAttribute("reservas", misReservas);
        return "mis-reservas";
    }

    @PostMapping("/crear")
    public String crearReserva(@RequestParam Long hotelId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            ReservaRequest request = new ReservaRequest(
                    cliente.getId(),
                    hotelId,
                    fechaEntrada,
                    fechaSalida
            );

            reservaService.create(request);

            redirectAttributes.addFlashAttribute("exito", "¡Reserva confirmada con éxito! Prepara las botas de montaña.");
            return "redirect:/reservas/mis-reservas";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al reservar: " + e.getMessage());
            return "redirect:/hoteles/" + hotelId;
        }
    }

    @PostMapping("/cancelar")
    public String cancelarReserva(@RequestParam Long reservaId) {
        reservaService.cancelarReserva(reservaId);

        return "redirect:/reservas/mis-reservas?cancelada=true";
    }

    @GetMapping("/detalles/{id}")
    public String verDetallesReserva(@PathVariable Long id, Model model, Principal principal) {
        Reserva reserva = reservaService.obtenerReservaEntidad(id);

        if (principal == null || !reserva.getCliente().getEmail().equals(principal.getName())) {
            return "redirect:/reservas/mis-reservas";
        }

        model.addAttribute("reserva", reserva);
        return "reserva-detalle";
    }
}