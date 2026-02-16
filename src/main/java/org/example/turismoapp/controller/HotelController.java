package org.example.turismoapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.HotelRequest;
import org.example.turismoapp.dto.HotelResponse;
import org.example.turismoapp.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con hoteles.
 * Proporciona endpoints para crear, leer, actualizar y eliminar hoteles.
 */
@RestController
@RequestMapping("/api/hoteles")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    /**
     * Obtiene una lista de todos los hoteles registrados.
     *
     * @return ResponseEntity con la lista de objetos HotelResponse.
     */
    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    /**
     * Obtiene los detalles de un hotel específico por su ID.
     *
     * @param id El ID del hotel a buscar.
     * @return ResponseEntity con el objeto HotelResponse si se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    /**
     * Crea un nuevo hotel.
     *
     * @param request Objeto HotelRequest con los datos del nuevo hotel.
     * @return ResponseEntity con el objeto HotelResponse creado y la ubicación del recurso.
     */
    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelRequest request) {
        HotelResponse createdHotel = hotelService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdHotel.id())
                .toUri();

        return ResponseEntity.created(location).body(createdHotel);
    }

    /**
     * Actualiza los datos de un hotel existente.
     *
     * @param id      El ID del hotel a actualizar.
     * @param request Objeto HotelRequest con los nuevos datos del hotel.
     * @return ResponseEntity con el objeto HotelResponse actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.update(id, request));
    }

    /**
     * Elimina un hotel por su ID.
     *
     * @param id El ID del hotel a eliminar.
     * @return ResponseEntity con estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
