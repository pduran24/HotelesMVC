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

import java.security.Principal;

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

        model.addAttribute("cliente", cliente);
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