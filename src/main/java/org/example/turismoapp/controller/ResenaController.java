package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.ResenaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping("/crear")
    public String crearResena(@RequestParam Long hotelId,
                              @RequestParam Integer puntuacion,
                              @RequestParam String comentario,
                              Principal principal) {

        // Solo guardamos si el usuario está logueado
        if (principal != null) {
            resenaService.crearResena(hotelId, principal.getName(), puntuacion, comentario);
        }

        // Redirigimos de vuelta a la página del hotel para ver los cambios
        return "redirect:/hoteles/" + hotelId;
    }

}
