package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.HotelRequest;
import org.example.turismoapp.dto.HotelResponse;
import org.example.turismoapp.exception.HotelNotFoundException;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con los hoteles.
 */
@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Recupera todos los hoteles disponibles.
     *
     * @return Lista de objetos HotelResponse que representan todos los hoteles.
     */
    @Transactional(readOnly = true)
    public List<HotelResponse> findAll() {
        return hotelRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Busca un hotel por su ID.
     *
     * @param id El ID del hotel a buscar.
     * @return Objeto HotelResponse con los detalles del hotel.
     * @throws HotelNotFoundException si no se encuentra un hotel con el ID proporcionado.
     */
    @Transactional(readOnly = true)
    public HotelResponse findById(Long id) {
        return hotelRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new HotelNotFoundException("Hotel con id " + id + " no encontrado"));
    }

    /**
     * Crea un nuevo hotel en el sistema.
     *
     * @param request Objeto HotelRequest con la información del nuevo hotel.
     * @return Objeto HotelResponse con los detalles del hotel creado.
     */
    @Transactional
    public HotelResponse create(HotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setNombre(request.nombre());
        hotel.setUbicacion(request.ubicacion());
        hotel.setDescripcion(request.descripcion());
        hotel.setEstrellas(request.estrellas());
        hotel.setPrecioNoche(request.precioNoche()); // Recuerda: BigDecimal

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToResponse(savedHotel);
    }

    /**
     * Actualiza la información de un hotel existente.
     *
     * @param id      El ID del hotel a actualizar.
     * @param request Objeto HotelRequest con la nueva información del hotel.
     * @return Objeto HotelResponse con los detalles del hotel actualizado.
     * @throws HotelNotFoundException si no se encuentra el hotel a actualizar.
     */
    @Transactional
    public HotelResponse update(Long id, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel no encontrado"));

        hotel.setNombre(request.nombre());
        hotel.setUbicacion(request.ubicacion());
        hotel.setDescripcion(request.descripcion());
        hotel.setEstrellas(request.estrellas());
        hotel.setPrecioNoche(request.precioNoche());

        Hotel updatedHotel = hotelRepository.save(hotel);

        return mapToResponse(updatedHotel);
    }

    /**
     * Elimina un hotel del sistema.
     *
     * @param id El ID del hotel a eliminar.
     * @throws HotelNotFoundException si el hotel no existe.
     */
    @Transactional
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException("No se puede borrar. Hotel no encontrado.");
        }
        hotelRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Hotel a un DTO HotelResponse.
     *
     * @param hotel La entidad Hotel a convertir.
     * @return El objeto HotelResponse resultante.
     */
    private HotelResponse mapToResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getNombre(),
                hotel.getUbicacion(),
                hotel.getDescripcion(),
                hotel.getEstrellas(),
                hotel.getPrecioNoche()
        );
    }
}
