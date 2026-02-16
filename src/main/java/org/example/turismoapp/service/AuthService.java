package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.dto.ClienteRequest;
import org.example.turismoapp.exception.UsuarioYaExistenteException;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio encargado de la orquestación de la autenticación y el registro.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClienteService clienteService;

    /**
     * Registra un nuevo usuario en el sistema, creando sus credenciales de acceso
     * y su perfil de cliente asociado.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña plana.
     * @throws RuntimeException si el usuario ya existe en la base de datos.
     */
    @Transactional
    public void registrarNuevoUsuario(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsuarioYaExistenteException("El nombre de usuario ya está en uso");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("USER");
        userRepository.save(newUser);


        ClienteRequest clienteRequest = new ClienteRequest(username);
        clienteService.create(clienteRequest);
    }
}