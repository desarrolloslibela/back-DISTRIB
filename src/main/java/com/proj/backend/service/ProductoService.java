package com.proj.backend.service;

import com.proj.backend.dto.ProductoDTO;
import org.springframework.data.domain.*;
import java.util.Optional;

public interface ProductoService {
    Page<ProductoDTO> buscarConFiltros(String nombre, Long tipoProductoId, Boolean activo, Pageable pageable);
    ProductoDTO crear(ProductoDTO dto);
    ProductoDTO actualizar(Long id, ProductoDTO dto);
    void eliminar(Long id);
    Optional<ProductoDTO> obtenerPorId(Long id);
}