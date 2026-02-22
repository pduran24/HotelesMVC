package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.FechasOcupadasDTO;
import org.example.turismoapp.dto.ReservaRequest;
import org.example.turismoapp.dto.ReservaResponse;
import org.example.turismoapp.exception.ClienteNotFoundException;
import org.example.turismoapp.exception.HotelNotFoundException;
import org.example.turismoapp.exception.ReservaNoEncontradaException;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.model.Reserva;
import org.example.turismoapp.model.enums.EstadoReserva;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con las reservas.
 */
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HotelRepository hotelRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Recupera todas las reservas registradas.
     *
     * @return Lista de objetos ReservaResponse que representan todas las reservas.
     */
    @Transactional(readOnly = true)
    public List<ReservaResponse> findAll() {
        return reservaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id El ID de la reserva a buscar.
     * @return Objeto ReservaResponse con los detalles de la reserva.
     * @throws ReservaNoEncontradaException si no se encuentra una reserva con el ID proporcionado.
     */
    @Transactional(readOnly = true)
    public ReservaResponse findById(Long id) {
        return reservaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ReservaNoEncontradaException("Reserva con id " + id + " no encontrada"));
    }

    /**
     * Crea una nueva reserva en el sistema.
     * Valida que la fecha de salida sea posterior a la de entrada y calcula el precio total.
     *
     * @param request Objeto ReservaRequest con la información de la nueva reserva.
     * @return Objeto ReservaResponse con los detalles de la reserva creada.
     * @throws IllegalArgumentException si la fecha de salida no es posterior a la de entrada.
     * @throws HotelNotFoundException si el hotel especificado no existe.
     * @throws ClienteNotFoundException si el cliente especificado no existe.
     */
    @Transactional
    public ReservaResponse create(ReservaRequest request) {
        if (!request.fechaSalida().isAfter(request.fechaEntrada())) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
        }

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel no encontrado con id: " + request.hotelId()));

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con id: " + request.clienteId()));

        long dias = ChronoUnit.DAYS.between(request.fechaEntrada(), request.fechaSalida());

        BigDecimal precioTotal = hotel.getPrecioNoche().multiply(BigDecimal.valueOf(dias));

        Reserva reserva = new Reserva();
        reserva.setFechaEntrada(request.fechaEntrada());
        reserva.setFechaSalida(request.fechaSalida());
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reserva.setPrecioTotal(precioTotal);
        reserva.setHotel(hotel);
        reserva.setCliente(cliente);

        Reserva savedReserva = reservaRepository.save(reserva);
        return mapToResponse(savedReserva);
    }


    /**
     * Elimina una reserva del sistema.
     *
     * @param id El ID de la reserva a eliminar.
     * @throws ReservaNoEncontradaException si la reserva no existe.
     */
    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> obtenerReservasPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);

        if (cliente == null) {
            return List.of();
        }

        List<Reserva> reservas = reservaRepository.findByCliente(cliente);

        return reservas.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FechasOcupadasDTO> obtenerFechasOcupadasPorHotel(Long hotelId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return reservaRepository.findByHotel_Id(hotelId).stream()
                .filter(reserva -> reserva.getEstado().name().equals("CONFIRMADA"))
                .map(reserva -> new FechasOcupadasDTO(
                        reserva.getFechaEntrada().format(formatter),
                        reserva.getFechaSalida().format(formatter)
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public Long countByCliente_Email(String email) {
        return reservaRepository.countByCliente_Email(email);
    }

    @Transactional(readOnly = true)
    public List<FechasOcupadasDTO> obtenerFechasReservadasPorCliente(String email) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return reservaRepository.findByCliente_Email(email).stream()
                // Usamos directamente tu Enum EstadoReserva
                .filter(reserva -> reserva.getEstado() == EstadoReserva.CONFIRMADA)
                .map(reserva -> new FechasOcupadasDTO(
                        reserva.getFechaEntrada().format(formatter),
                        reserva.getFechaSalida().format(formatter)
                ))
                .toList();
    }

    /**
     * Convierte una entidad Reserva a un DTO ReservaResponse.
     *
     * @param reserva La entidad Reserva a convertir.
     * @return El objeto ReservaResponse resultante.
     */
    private ReservaResponse mapToResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getHotel().getNombre(),
                reserva.getCliente().getNombre(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                reserva.getNumeroNoches(),
                reserva.getPrecioTotal(),
                reserva.getEstado()
        );
    }
}
