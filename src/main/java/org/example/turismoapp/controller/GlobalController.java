package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.FechasOcupadasDTO;
import org.example.turismoapp.repository.ReservaRepository;
import org.example.turismoapp.service.FavoritoService;
import org.example.turismoapp.service.ReservaService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalController {
    private final ReservaService reservaService;
    private final FavoritoService favoritoService;

    @ModelAttribute("numReservas")
    public Long cargarNumeroReservas(Principal principal) {

        if (principal == null) {
            return 0L;
        }


        return reservaService.countByCliente_Email(principal.getName());
    }

    @ModelAttribute("numFavoritos")
    public Integer contarFavoritosGlobales(Principal principal) {
        if (principal == null) {
            return 0;
        }
        return favoritoService.obtenerIdsFavoritos(principal.getName()).size();
    }

    @ModelAttribute("misFechasReservadas")
    public List<FechasOcupadasDTO> misFechasReservadasGlobal(Principal principal) {
        if (principal == null) {
            return List.of();
        }
        return reservaService.obtenerFechasReservadasPorCliente(principal.getName());
    }
}
