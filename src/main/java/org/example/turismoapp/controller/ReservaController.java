package org.example.turismoapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ReservaRequest;
import org.example.turismoapp.dto.ReservaResponse;
import org.example.turismoapp.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con reservas.
 * Proporciona endpoints para crear, leer y eliminar reservas.
 */
@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Obtiene una lista de todas las reservas registradas.
     *
     * @return ResponseEntity con la lista de objetos ReservaResponse.
     */
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> getAllReservas() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    /**
     * Obtiene los detalles de una reserva específica por su ID.
     *
     * @param id El ID de la reserva a buscar.
     * @return ResponseEntity con el objeto ReservaResponse si se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getReservaById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    /**
     * Crea una nueva reserva.
     *
     * @param request Objeto ReservaRequest con los datos de la nueva reserva.
     * @return ResponseEntity con el objeto ReservaResponse creado y la ubicación del recurso.
     */
    @PostMapping
    public ResponseEntity<ReservaResponse> createReserva(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse createdReserva = reservaService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdReserva.id())
                .toUri();

        return ResponseEntity.created(location).body(createdReserva);
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id El ID de la reserva a eliminar.
     * @return ResponseEntity con estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
