package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MercadoService {

    private static final Logger log = LoggerFactory.getLogger(MercadoService.class);
    private final HotelRepository hotelRepository;
    private final AlertaService alertaService;
    private final Random random = new Random();

    /**
     * @Scheduled(fixedRate = 120000) -> Se ejecuta cada 120 segundos
     */
    @Scheduled(fixedRate = 120000)
    @Transactional
    public void simularFluctuacionDePreciosDiaria() {

        List<Hotel> hoteles = hotelRepository.findAll();
        if (hoteles.isEmpty()) return;

        int ofertasFlashGeneradas = 0;

        for (Hotel hotel : hoteles) {
            BigDecimal precioActual = hotel.getPrecioNoche();
            BigDecimal nuevoPrecio;

            if (random.nextInt(100) < 10) {
                double descuento = 0.15 + (0.15 * random.nextDouble());
                nuevoPrecio = precioActual.multiply(BigDecimal.valueOf(1.0 - descuento));
                ofertasFlashGeneradas++;
                log.info("⚡ OFERTA FLASH en {}: Bajó de {}€ a {}€", hotel.getNombre(), precioActual, nuevoPrecio.setScale(2, RoundingMode.HALF_UP));
            }
            else {
                double fluctuacion = -0.05 + (0.10 * random.nextDouble());
                nuevoPrecio = precioActual.multiply(BigDecimal.valueOf(1.0 + fluctuacion));
            }

            if (nuevoPrecio.compareTo(BigDecimal.valueOf(20.0)) < 0) {
                nuevoPrecio = BigDecimal.valueOf(20.0);
            } else if (nuevoPrecio.compareTo(BigDecimal.valueOf(800.0)) > 0) {
                nuevoPrecio = BigDecimal.valueOf(800.0);
            }

            hotel.setPrecioNoche(nuevoPrecio.setScale(2, RoundingMode.HALF_UP));
        }

        hotelRepository.saveAll(hoteles);

        alertaService.comprobarAlertas(hoteles);
    }
}