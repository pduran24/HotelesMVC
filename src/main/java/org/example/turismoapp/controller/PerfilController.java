package org.example.turismoapp.web;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository; // Opcional, por si queremos datos del login

    @GetMapping
    public String verMiPerfil(Model model, Principal principal) {
        // 1. Obtener el nombre (email/username) del usuario logueado
        String username = principal.getName();

        // 2. Buscar sus datos de Cliente en la BD
        Cliente cliente = clienteRepository.findByEmail(username)
                .orElse(null);

        // 3. Si es el admin o un usuario sin perfil de cliente, manejamos el caso
        if (cliente == null) {
            // Podríamos crear un cliente al vuelo o redirigir
            // Por simplicidad, pasamos un objeto vacío o mostramos error
            // (En tu caso, si entras como 'admin', no tendrás perfil de cliente)
            return "redirect:/hoteles";
        }

        // 4. Pasar los datos a la vista
        model.addAttribute("cliente", cliente);

        return "perfil"; // Nombre del archivo HTML
    }
}