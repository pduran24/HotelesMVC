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
     * @param nombre Nombre de usuario.
     * @param email Email del usuario
     * @param password Contraseña plana.
     * @throws RuntimeException si el usuario ya existe en la base de datos.
     */
    @Transactional
    public void registrarNuevoUsuario(String nombre, String email, String password) { // <--- 3 Argumentos

        if (userRepository.findByUsername(email).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(email);
        newUser.setPassword(password);
        newUser.setRole("USER");
        userRepository.save(newUser);


        ClienteRequest clienteRequest = new ClienteRequest(nombre, email, null, null);
        clienteService.create(clienteRequest);
    }
}