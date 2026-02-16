package org.example.turismoapp.exception;

/**
 * Excepción lanzada cuando no se encuentra un hotel solicitado.
 */
public class HotelNotFoundException extends RuntimeException {
    /**
     * Constructor con mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public HotelNotFoundException(String message) {
        super(message);
    }
}
