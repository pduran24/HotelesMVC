package org.example.turismoapp.service;

import org.example.turismoapp.dto.ReservaRequest;
import org.example.turismoapp.exception.HotelNotFoundException;
import org.example.turismoapp.exception.ClienteNotFoundException;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.model.Reserva;
import org.example.turismoapp.model.enums.EstadoReserva;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock ReservaRepository reservaRepository;
    @Mock HotelRepository hotelRepository;
    @Mock ClienteRepository clienteRepository;
    @Mock EmailService emailService;

    @InjectMocks ReservaService reservaService;

    private Hotel hotelDePrueba() {
        Hotel h = new Hotel();
        h.setId(1L);
        h.setNombre("Hotel Test");
        h.setUbicacion("Ordesa");
        h.setPrecioNoche(new BigDecimal("100.00"));
        return h;
    }

    private Cliente clienteDePrueba() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNombre("Cliente Test");
        c.setEmail("test@test.com");
        return c;
    }

    @Test
    void create_reservaValida_calculaPrecioYGuarda() {
        LocalDate entrada = LocalDate.now().plusDays(10);
        LocalDate salida = LocalDate.now().plusDays(13); // 3 noches

        ReservaRequest request = new ReservaRequest(1L, 1L, entrada, salida);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelDePrueba()));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteDePrueba()));
        when(reservaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        reservaService.create(request);

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepository).save(captor.capture());
        Reserva guardada = captor.getValue();

        assertThat(guardada.getPrecioTotal()).isEqualByComparingTo("300.00");
        assertThat(guardada.getEstado()).isEqualTo(EstadoReserva.CONFIRMADA);
        verify(emailService).enviarBonoReserva(guardada);
    }

    @Test
    void create_fechaSalidaIgualEntrada_lanzaIllegalArgument() {
        LocalDate fecha = LocalDate.now().plusDays(5);
        ReservaRequest request = new ReservaRequest(1L, 1L, fecha, fecha);

        assertThatThrownBy(() -> reservaService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("fecha de salida");

        verify(reservaRepository, never()).save(any());
    }

    @Test
    void create_fechaSalidaAnteriorEntrada_lanzaIllegalArgument() {
        LocalDate entrada = LocalDate.now().plusDays(10);
        LocalDate salida = LocalDate.now().plusDays(5);
        ReservaRequest request = new ReservaRequest(1L, 1L, entrada, salida);

        assertThatThrownBy(() -> reservaService.create(request))
                .isInstanceOf(IllegalArgumentException.class);

        verify(reservaRepository, never()).save(any());
    }

    @Test
    void create_hotelNoExiste_lanzaHotelNotFoundException() {
        LocalDate entrada = LocalDate.now().plusDays(1);
        LocalDate salida = LocalDate.now().plusDays(3);
        ReservaRequest request = new ReservaRequest(1L, 99L, entrada, salida);

        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.create(request))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    void create_clienteNoExiste_lanzaClienteNotFoundException() {
        LocalDate entrada = LocalDate.now().plusDays(1);
        LocalDate salida = LocalDate.now().plusDays(3);
        ReservaRequest request = new ReservaRequest(99L, 1L, entrada, salida);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelDePrueba()));
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.create(request))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void cancelarReserva_existente_cambiaaEstadoCancelada() {
        Reserva reserva = Reserva.builder()
                .id(1L)
                .estado(EstadoReserva.CONFIRMADA)
                .build();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        reservaService.cancelarReserva(1L);

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepository).save(captor.capture());
        assertThat(captor.getValue().getEstado()).isEqualTo(EstadoReserva.CANCELADA);
    }

    @Test
    void cancelarReserva_noExiste_lanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.cancelarReserva(99L))
                .isInstanceOf(RuntimeException.class);
    }
}