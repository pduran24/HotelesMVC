package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.AlertaPrecioRepository;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.ReservaRepository;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final AlertaPrecioRepository alertaPrecioRepository;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalUsuarios", userRepository.count());
        model.addAttribute("totalHoteles", hotelRepository.count());
        model.addAttribute("totalReservas", reservaRepository.count());
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("usuarios", userRepository.findAll());
        return "admin/dashboard";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    @Transactional
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UserEntity usuario = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

        if ("ADMIN".equals(usuario.getRole())) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar un usuario ADMIN.");
            return "redirect:/admin";
        }

        clienteRepository.findByEmail(usuario.getUsername()).ifPresent(cliente -> {
            alertaPrecioRepository.deleteAll(
                    alertaPrecioRepository.findByCliente_EmailOrderByIdDesc(cliente.getEmail())
            );
            clienteRepository.delete(cliente);
        });
        userRepository.delete(usuario);

        redirectAttributes.addFlashAttribute("exito", "Usuario '" + usuario.getUsername() + "' eliminado correctamente.");
        return "redirect:/admin";
    }
}