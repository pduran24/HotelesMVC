package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClienteRequest;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.service.ClienteService;
import org.example.turismoapp.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String verMiPerfil(Model model, Principal principal) {
        String username = principal.getName();
        Cliente cliente = clienteRepository.findByEmail(username).orElse(null);

        if (cliente == null) {
            return "redirect:/hoteles";
        }

        long totalNoches = 0;
        BigDecimal totalGasto = BigDecimal.ZERO;

        Map<String, Long> destinosConteo = new LinkedHashMap<>();
        Map<String, Long> mesesConteo = new LinkedHashMap<>();

        if (cliente.getReservas() != null) {
            for (var reserva : cliente.getReservas()) {
                long noches = ChronoUnit.DAYS.between(
                        reserva.getFechaEntrada(),
                        reserva.getFechaSalida()
                );

                totalNoches += noches;

                BigDecimal precioNoche = reserva.getHotel().getPrecioNoche();
                BigDecimal totalReserva = precioNoche.multiply(BigDecimal.valueOf(noches));

                totalGasto = totalGasto.add(totalReserva);
                String nombreHotel = reserva.getHotel().getNombre();
                destinosConteo.put(nombreHotel, destinosConteo.getOrDefault(nombreHotel, 0L) + 1);


                String mesAnyo = reserva.getFechaEntrada().getYear() + "-" + String.format("%02d", reserva.getFechaEntrada().getMonthValue());
                mesesConteo.put(mesAnyo, mesesConteo.getOrDefault(mesAnyo, 0L) + 1);
            }
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("totalNoches", totalNoches);
        model.addAttribute("totalGasto", totalGasto);

        model.addAttribute("destinosLabels", destinosConteo.keySet());
        model.addAttribute("destinosData", destinosConteo.values());

        model.addAttribute("mesesLabels", mesesConteo.keySet());
        model.addAttribute("mesesData", mesesConteo.values());

        return "perfil";
    }

    @PostMapping("/editar")
    public String editarPerfil(
            @RequestParam("nombre") String nombre,
            @RequestParam("biografia") String biografia,
            @RequestParam("telefono") String telefono,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        String username = principal.getName();
        Cliente cliente = clienteRepository.findByEmail(username).orElse(null);

        if (cliente != null) {
            try {
                ClienteRequest updateRequest = new ClienteRequest(nombre, username, biografia, telefono);
                clienteService.update(cliente.getId(), updateRequest);

                if (avatarFile != null && !avatarFile.isEmpty()) {
                    String avatarUrl = fileStorageService.guardarAvatar(avatarFile);
                    clienteService.updateAvatar(cliente.getId(), avatarUrl);
                }

                redirectAttributes.addFlashAttribute("mensajeExito", "¡Perfil actualizado correctamente!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("mensajeError", "Hubo un error al actualizar: " + e.getMessage());
            }
        }

        return "redirect:/perfil";
    }
}