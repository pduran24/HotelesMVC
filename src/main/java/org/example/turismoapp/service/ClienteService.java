package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClienteRequest;
import org.example.turismoapp.dto.ClienteResponse;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con los clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Recupera todos los clientes registrados.
     *
     * @return Lista de objetos ClienteResponse que representan todos los clientes.
     */
    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return Objeto ClienteResponse con los detalles del cliente.
     * @throws RuntimeException si no se encuentra un cliente con el ID proporcionado.
     */
    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no encontrado"));
    }

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param request Objeto ClienteRequest con la información del nuevo cliente.
     * @return Objeto ClienteResponse con los detalles del cliente creado.
     */
    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());

        Cliente saved = clienteRepository.save(cliente);
        return mapToResponse(saved);
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param id      El ID del cliente a actualizar.
     * @param request Objeto ClienteRequest con la nueva información del cliente.
     * @return Objeto ClienteResponse con los detalles del cliente actualizado.
     * @throws RuntimeException si no se encuentra el cliente a actualizar.
     */
    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(request.nombre());

        return mapToResponse(clienteRepository.save(cliente));
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id El ID del cliente a eliminar.
     * @throws RuntimeException si el cliente no existe.
     */
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cliente no encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Cliente a un DTO ClienteResponse.
     *
     * @param cliente La entidad Cliente a convertir.
     * @return El objeto ClienteResponse resultante.
     */
    private ClienteResponse mapToResponse(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNombre());
    }
}
