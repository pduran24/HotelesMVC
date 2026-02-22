package org.example.turismoapp.service;

import org.example.turismoapp.model.Ruta;
import org.example.turismoapp.repository.RutaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public List<Ruta> obtenerTodas() {
        return rutaRepository.findAll();
    }

    public Ruta obtenerPorId(Long id) {
        return rutaRepository.findById(id).orElse(null);
    }
}