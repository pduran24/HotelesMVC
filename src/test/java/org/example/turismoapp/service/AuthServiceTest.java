package org.example.turismoapp.service;

import org.example.turismoapp.dto.ClienteRequest;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserRepository userRepository;
    @Mock ClienteService clienteService;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks AuthService authService;

    @Test
    void registrar_usuarioNuevo_guardaUserYCreaCliente() {
        when(userRepository.findByUsername("nuevo@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass123")).thenReturn("$2a$hashed");
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        authService.registrarNuevoUsuario("Nuevo Usuario", "nuevo@test.com", "pass123");

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity guardado = captor.getValue();

        assertThat(guardado.getUsername()).isEqualTo("nuevo@test.com");
        assertThat(guardado.getPassword()).isEqualTo("$2a$hashed");
        assertThat(guardado.getRole()).isEqualTo("USER");

        verify(clienteService).create(new ClienteRequest("Nuevo Usuario", "nuevo@test.com", null, null));
    }

    @Test
    void registrar_emailDuplicado_lanzaExcepcion() {
        when(userRepository.findByUsername("existente@test.com"))
                .thenReturn(Optional.of(new UserEntity()));

        assertThatThrownBy(() ->
                authService.registrarNuevoUsuario("Test", "existente@test.com", "pass"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ya está registrado");

        verify(userRepository, never()).save(any());
        verify(clienteService, never()).create(any());
    }

    @Test
    void registrar_passwordNuncaSeGuardaEnPlano() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("miPassword")).thenReturn("$2a$encoded");
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        authService.registrarNuevoUsuario("Test", "test@test.com", "miPassword");

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isNotEqualTo("miPassword");
        verify(passwordEncoder).encode("miPassword");
    }
}