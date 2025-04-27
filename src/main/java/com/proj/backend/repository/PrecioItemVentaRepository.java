package com.proj.backend.repository;

import com.proj.backend.model.PrecioItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecioItemVentaRepository extends JpaRepository<PrecioItemVenta, Long> {
    List<PrecioItemVenta> findByListaId(Long listaId);
}