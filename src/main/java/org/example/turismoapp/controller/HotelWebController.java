package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClimaDTO;
import org.example.turismoapp.dto.HotelResponse;
import org.example.turismoapp.dto.PronosticoDiarioDTO;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listarHoteles(
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Integer estrellas,
            @RequestParam(required = false) Boolean admiteMascotas,
            @RequestParam(required = false) Boolean tieneSpa,
            Model model) {

        List<HotelResponse> hoteles = hotelService.buscarConFiltros(
                ubicacion, precioMin, precioMax, estrellas, admiteMascotas, tieneSpa
        );

        model.addAttribute("listaHoteles", hoteles);

        model.addAttribute("ubicacionFiltro", ubicacion);
        model.addAttribute("precioMinFiltro", precioMin);
        model.addAttribute("precioMaxFiltro", precioMax);
        model.addAttribute("estrellasFiltro", estrellas);
        model.addAttribute("mascotasFiltro", admiteMascotas != null ? admiteMascotas : false);
        model.addAttribute("spaFiltro", tieneSpa != null ? tieneSpa : false);

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
