package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final ClienteRepository clienteRepository;
    private final HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public Set<Hotel> obtenerFavoritos(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElse(null);

        if (cliente == null) {
            return Set.of();
        }

        Set<Hotel> favoritos = cliente.getFavoritos();
        favoritos.size();

        return favoritos;
    }

    @Transactional
    public void toggleFavorito(String email, Long hotelId) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();

        if (cliente.getFavoritos().contains(hotel)) {
            cliente.getFavoritos().remove(hotel);
        } else {
            cliente.getFavoritos().add(hotel);
        }
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Set<Long> obtenerIdsFavoritos(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        if (cliente == null) return Set.of();

        return cliente.getFavoritos().stream()
                .map(Hotel::getId)
                .collect(java.util.stream.Collectors.toSet());
    }
}
