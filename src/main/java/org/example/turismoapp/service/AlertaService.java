package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.AlertaPrecio;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.AlertaPrecioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private static final Logger log = LoggerFactory.getLogger(AlertaService.class);
    private final AlertaPrecioRepository alertaRepository;

    @Transactional
    public void crearAlerta(Cliente cliente, Hotel hotel, BigDecimal precioDeseado) {
        AlertaPrecio alerta = new AlertaPrecio();
        alerta.setCliente(cliente);
        alerta.setHotel(hotel);
        alerta.setPrecioObjetivo(precioDeseado);
        alerta.setNotificada(false);

        alertaRepository.save(alerta);
    }

    @Transactional
    public void comprobarAlertas(List<Hotel> hoteles) {
        for (Hotel hotel : hoteles) {
            List<AlertaPrecio> alertasPendientes = alertaRepository.findByHotelAndNotificadaFalse(hotel);

            for (AlertaPrecio alerta : alertasPendientes) {
                if (hotel.getPrecioNoche().compareTo(alerta.getPrecioObjetivo()) <= 0) {

                    alerta.setNotificada(true);
                    alertaRepository.save(alerta);

                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<AlertaPrecio> obtenerMisAlertas(String email) {
        return alertaRepository.findByCliente_EmailOrderByIdDesc(email);
    }

    @Transactional(readOnly = true)
    public int contarAlertasActivas(String email) {
        return alertaRepository.countByCliente_EmailAndNotificadaFalse(email);
    }

    @Transactional
    public void eliminarAlerta(Long id, String email) {
        AlertaPrecio alerta = alertaRepository.findById(id).orElse(null);
        if (alerta != null && alerta.getCliente().getEmail().equals(email)) {
            alertaRepository.delete(alerta);
            log.info("Alerta eliminada por el usuario {}", email);
        }
    }
}