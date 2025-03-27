package com.proj.backend.service;

import com.proj.backend.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductoService {

    Page<ProductoDTO> buscarConFiltros(String nombre, Long tipoProductoId, Double capacidad, Boolean activo, Pageable pageable);

    Optional<ProductoDTO> obtenerPorId(Long id);

    ProductoDTO crear(ProductoDTO dto);

    ProductoDTO actualizar(Long id, ProductoDTO dto);

    void eliminar(Long id);
}