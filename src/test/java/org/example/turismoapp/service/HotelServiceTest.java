package org.example.turismoapp.service;

import org.example.turismoapp.exception.HotelNotFoundException;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock HotelRepository hotelRepository;
    @Mock ResenaService resenaService;

    @InjectMocks HotelService hotelService;

    private Hotel hotelDePrueba() {
        Hotel h = new Hotel();
        h.setId(1L);
        h.setNombre("Refugio Góriz");
        h.setUbicacion("Ordesa");
        h.setDescripcion("El refugio más alto");
        h.setEstrellas(3);
        h.setPrecioNoche(new BigDecimal("95.00"));
        h.setLatitud(42.66);
        h.setLongitud(0.04);
        return h;
    }

    @Test
    void findById_hotelExiste_devuelveResponse() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelDePrueba()));

        var response = hotelService.findById(1L);

        assertThat(response.nombre()).isEqualTo("Refugio Góriz");
        assertThat(response.ubicacion()).isEqualTo("Ordesa");
        assertThat(response.estrellas()).isEqualTo(3);
        assertThat(response.precioNoche()).isEqualByComparingTo("95.00");
    }

    @Test
    void findById_hotelNoExiste_lanzaHotelNotFoundException() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.findById(99L))
                .isInstanceOf(HotelNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void delete_hotelExiste_llamaDeleteById() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        hotelService.delete(1L);

        verify(hotelRepository).deleteById(1L);
    }

    @Test
    void delete_hotelNoExiste_lanzaHotelNotFoundException() {
        when(hotelRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> hotelService.delete(99L))
                .isInstanceOf(HotelNotFoundException.class);

        verify(hotelRepository, never()).deleteById(any());
    }

    @Test
    void findAll_devuelveListaMapeada() {
        when(hotelRepository.findAll(
                    ArgumentMatchers.<Specification<Hotel>>any()
                )).thenReturn(List.of(hotelDePrueba()));

        var resultado = hotelService.buscarConFiltros(null, null, null, null, null, null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().nombre()).isEqualTo("Refugio Góriz");
    }
}