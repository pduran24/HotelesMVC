package org.example.turismoapp.controller;

import org.example.turismoapp.exception.UsuarioYaExistenteException;
import org.example.turismoapp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombre,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        try {
            authService.registrarNuevoUsuario(nombre, username, password);

            redirectAttributes.addAttribute("registrado", true);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorRegistro", e.getMessage());
            return "registro";
        }
    }

}
