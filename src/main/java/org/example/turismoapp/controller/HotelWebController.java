package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
