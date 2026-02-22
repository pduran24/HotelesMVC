package org.example.turismoapp.repository;

import org.example.turismoapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Hotel.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
        /**
        * Método para encontrar hoteles por su nombre.
        *
        * @param nombre El nombre a buscar.
        * @return Hotel que coincide con el nombre dado.
        */
        Optional<Hotel> findByNombre(String nombre);
}
