package com.proj.backend.service;

import com.proj.backend.dto.ListaPrecioVentaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ListaPrecioVentaService {
    ListaPrecioVentaDTO crear(ListaPrecioVentaDTO dto);
    ListaPrecioVentaDTO actualizar(Long id, ListaPrecioVentaDTO dto);
    void eliminar(Long id);
    ListaPrecioVentaDTO obtenerPorId(Long id);
    Page<ListaPrecioVentaDTO> filtrar(LocalDate desde, LocalDate hasta, Pageable pageable);
}