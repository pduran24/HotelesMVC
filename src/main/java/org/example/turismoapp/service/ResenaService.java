package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.model.Resena;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.ResenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final HotelRepository hotelRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public void crearResena(Long hotelId, String emailCliente, Integer puntuacion, String comentario) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Cliente cliente = clienteRepository.findByEmail(emailCliente).orElseThrow();

        Resena resena = resenaRepository.findByHotel_IdAndCliente_Email(hotelId, emailCliente)
                .orElse(new Resena());

        resena.setHotel(hotel);
        resena.setCliente(cliente);
        resena.setPuntuacion(puntuacion);
        resena.setComentario(comentario);
        resena.setFecha(LocalDate.now());

        resenaRepository.save(resena);
    }

    @Transactional(readOnly = true)
    public Resena obtenerResenaPorClienteYHotel(Long hotelId, String email) {
        return resenaRepository.findByHotel_IdAndCliente_Email(hotelId, email).orElse(null);
    }
}