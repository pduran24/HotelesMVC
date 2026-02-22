package org.example.turismoapp.controller;

import org.example.turismoapp.model.Ruta;
import org.example.turismoapp.service.RutaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaRestController {

    private final RutaService rutaService;

    public RutaRestController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping
    public List<Ruta> obtenerTodasLasRutas() {
        return rutaService.obtenerTodas();
    }
}