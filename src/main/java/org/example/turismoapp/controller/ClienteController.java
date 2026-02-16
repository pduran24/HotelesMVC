package org.example.turismoapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClienteRequest;
import org.example.turismoapp.dto.ClienteResponse;
import org.example.turismoapp.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con clientes.
 * Proporciona endpoints para crear, leer, actualizar y eliminar clientes.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Obtiene una lista de todos los clientes registrados.
     *
     * @return ResponseEntity con la lista de objetos ClienteResponse.
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    /**
     * Obtiene los detalles de un cliente específico por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return ResponseEntity con el objeto ClienteResponse si se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    /**
     * Crea un nuevo cliente.
     *
     * @param request Objeto ClienteRequest con los datos del nuevo cliente.
     * @return ResponseEntity con el objeto ClienteResponse creado y la ubicación del recurso.
     */
    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse createdCliente = clienteService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCliente.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCliente);
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param id      El ID del cliente a actualizar.
     * @param request Objeto ClienteRequest con los nuevos datos del cliente.
     * @return ResponseEntity con el objeto ClienteResponse actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.update(id, request));
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id El ID del cliente a eliminar.
     * @return ResponseEntity con estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
