package org.example.turismoapp.repository;

import org.example.turismoapp.model.AlertaPrecio;
import org.example.turismoapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaPrecioRepository extends JpaRepository<AlertaPrecio, Long> {

    List<AlertaPrecio> findByHotelAndNotificadaFalse(Hotel hotel);
    List<AlertaPrecio> findByCliente_EmailOrderByIdDesc(String email);
    int countByCliente_EmailAndNotificadaFalse(String email);
}