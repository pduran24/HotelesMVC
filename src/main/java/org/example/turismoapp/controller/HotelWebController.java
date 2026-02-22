package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.FavoritoService;
import org.example.turismoapp.service.HotelService;
import org.example.turismoapp.service.ResenaService;
import org.example.turismoapp.service.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
@Controller
@RequestMapping("/hoteles")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;
    private final ReservaService reservaService;
    private final FavoritoService favoritoService;
    private final ResenaService resenaService;


    @GetMapping
    public String listarHoteles(Model model, Principal principal) {
        model.addAttribute("listaHoteles", hotelService.findAll());

        if (principal != null) {
            model.addAttribute("favoritosIds", favoritoService.obtenerIdsFavoritos(principal.getName()));
        }
        return "hoteles";
    }

    @GetMapping("/{id}")
    public String verDetalleHotel(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("hotel", hotelService.findById(id));
        model.addAttribute("fechasOcupadas", reservaService.obtenerFechasOcupadasPorHotel(id));

        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("favoritosIds", favoritoService.obtenerIdsFavoritos(email));

            model.addAttribute("miResena", resenaService.obtenerResenaPorClienteYHotel(id, email));
        }
        return "hotel-detalle";
    }

}
