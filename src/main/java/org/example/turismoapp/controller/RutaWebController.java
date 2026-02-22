package org.example.turismoapp.controller;

import org.example.turismoapp.model.Ruta;
import org.example.turismoapp.service.RutaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rutas")
public class RutaWebController {

    private final RutaService rutaService;

    public RutaWebController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping("/{id}")
    public String verDetalleRuta(@PathVariable Long id, Model model) {
        Ruta ruta = rutaService.obtenerPorId(id);

        if (ruta == null) {
            return "redirect:/hoteles";
        }

        model.addAttribute("ruta", ruta);
        return "ruta-detalle";
    }
}