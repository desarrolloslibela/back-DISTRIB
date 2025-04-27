package com.proj.backend.repository;

import com.proj.backend.model.ListaPrecioVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ListaPrecioVentaRepository extends JpaRepository<ListaPrecioVenta, Long> {
    List<ListaPrecioVenta> findByFechaDesdeLessThanEqualAndFechaHastaGreaterThanEqual(LocalDate fechaHasta, LocalDate fechaDesde);
}