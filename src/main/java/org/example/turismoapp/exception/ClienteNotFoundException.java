package org.example.turismoapp.exception;

/**
 * Excepción lanzada cuando no se encuentra un cliente solicitado.
 */
public class ClienteNotFoundException extends RuntimeException {
    /**
     * Constructor con mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
