package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.repository.ReservaRepository;
import org.example.turismoapp.service.FavoritoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalController {
    private final ReservaRepository reservaRepository;
    private final FavoritoService favoritoService;

    @ModelAttribute("numReservas")
    public Long cargarNumeroReservas(Principal principal) {

        if (principal == null) {
            return 0L;
        }


        return reservaRepository.countByCliente_Email(principal.getName());
    }

    @ModelAttribute("numFavoritos")
    public Integer contarFavoritosGlobales(Principal principal) {
        if (principal == null) {
            return 0;
        }
        return favoritoService.obtenerIdsFavoritos(principal.getName()).size();
    }
}
