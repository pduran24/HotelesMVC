package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.service.FavoritoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping
    public String verMisFavoritos(Model model, Principal principal) {
        String email = principal.getName();

        Set<Hotel> misFavoritos = favoritoService.obtenerFavoritos(email);

        model.addAttribute("hotelesFavoritos", misFavoritos);

        return "favoritos";
    }

    @PostMapping("/toggle")
    public String alternarFavorito(@RequestParam Long hotelId,
                                   @RequestParam String redirectUrl,
                                   Principal principal) {
        if (principal != null) {
            favoritoService.toggleFavorito(principal.getName(), hotelId);
        }
        return "redirect:" + redirectUrl;
    }
}
