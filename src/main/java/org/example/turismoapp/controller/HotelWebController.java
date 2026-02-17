package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hoteles")
@RequiredArgsConstructor
public class HotelWebController {

    private final HotelService hotelService;

    @GetMapping
    public String listarHoteles(Model model) {
        model.addAttribute("listaHoteles", hotelService.findAll());

        return "hoteles";
    }

    @GetMapping("/{id}")
    public String verDetalleHotel(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.findById(id));

        return "hotel-detalle";
    }
}
