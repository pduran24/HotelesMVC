package org.example.turismoapp.exception;

/**
 * Excepción lanzada cuando no se encuentra una reserva solicitada.
 */
public class ReservaNoEncontradaException extends RuntimeException {
    /**
     * Constructor con mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ReservaNoEncontradaException(String message) {
        super(message);
    }
}
