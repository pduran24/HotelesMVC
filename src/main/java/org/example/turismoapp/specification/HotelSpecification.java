package org.example.turismoapp.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.turismoapp.model.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {

    /**
     * Construye una consulta dinámica basada en los filtros proporcionados.
     */
    public static Specification<Hotel> conFiltrosMultiples(
            String ubicacion, Double precioMin, Double precioMax,
            Integer estrellas, Boolean admiteMascotas, Boolean tieneSpa) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicados = new ArrayList<>();

            // 1. Filtro por Ubicación (Búsqueda parcial, ignorando mayúsculas)
            if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                predicados.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("ubicacion")),
                        "%" + ubicacion.toLowerCase() + "%"
                ));
            }

            // 2. Filtro por Precio Mínimo
            if (precioMin != null) {
                predicados.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precioNoche"), precioMin));
            }

            // 3. Filtro por Precio Máximo
            if (precioMax != null) {
                predicados.add(criteriaBuilder.lessThanOrEqualTo(root.get("precioNoche"), precioMax));
            }

            // 4. Filtro exacto por Estrellas
            if (estrellas != null) {
                predicados.add(criteriaBuilder.equal(root.get("estrellas"), estrellas));
            }

            // 5. Filtros Booleanos (Amenities)
            if (admiteMascotas != null && admiteMascotas) {
                predicados.add(criteriaBuilder.isTrue(root.get("admiteMascotas")));
            }

            if (tieneSpa != null && tieneSpa) {
                predicados.add(criteriaBuilder.isTrue(root.get("tieneSpa")));
            }

            return criteriaBuilder.and(predicados.toArray(new Predicate[0]));
        };
    }
}