package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClimaDTO;
import org.example.turismoapp.dto.HotelResponse;
import org.example.turismoapp.dto.PronosticoDiarioDTO;
import org.example.turismoapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hoteles")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;
    private final ReservaService reservaService;
    private final FavoritoService favoritoService;
    private final ResenaService resenaService;
    private final ClimaService climaService;

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
        HotelResponse hotel = hotelService.findById(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("fechasOcupadas", reservaService.obtenerFechasOcupadasPorHotel(id));

        ClimaDTO climaActual = climaService.obtenerClimaActual(hotel.latitud(), hotel.longitud());
        if (climaActual != null) {
            model.addAttribute("clima", climaActual);
        }

        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("favoritosIds", favoritoService.obtenerIdsFavoritos(email));
            model.addAttribute("miResena", resenaService.obtenerResenaPorClienteYHotel(id, email));
        }

        return "hotel-detalle";
    }

    @GetMapping("/{id}/clima")
    public String verClimaHotel(@PathVariable Long id, Model model) {
        HotelResponse hotel = hotelService.findById(id);
        model.addAttribute("hotel", hotel);

        List<PronosticoDiarioDTO> pronostico = climaService.obtenerPronosticoSemanal(hotel.latitud(), hotel.longitud());
        model.addAttribute("pronostico", pronostico);

        return "hotel-clima";
    }

}
