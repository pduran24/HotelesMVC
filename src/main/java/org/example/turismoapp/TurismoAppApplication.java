package org.example.turismoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación TurismoApp.
 * Inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class TurismoAppApplication {

    /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(TurismoAppApplication.class, args);
    }

}
