package org.example.turismoapp.security;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para cargar los detalles del usuario desde la base de datos para la autenticación.
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return UserDetails con la información del usuario.
     * @throws UsernameNotFoundException si el usuario no se encuentra.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> currentUser = userRepository.findByUsername(username);

        if (currentUser.isEmpty()) {
            throw new UsernameNotFoundException("Username not found : " + username);
        }


        return User.withUsername(username)
                .password("{noop}" + currentUser.get().getPassword())
                .roles(currentUser.get().getRole())
                .build();
    }
}
