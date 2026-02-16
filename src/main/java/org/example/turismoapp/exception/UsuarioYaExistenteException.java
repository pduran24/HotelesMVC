package org.example.turismoapp.exception;

public class UsuarioYaExistenteException extends RuntimeException {
    public UsuarioYaExistenteException(String message) {
        super(message);
    }
}
