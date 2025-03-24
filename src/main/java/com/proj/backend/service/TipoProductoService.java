package com.proj.backend.service;

import com.proj.backend.dto.TipoProductoDTO;
import java.util.List;

public interface TipoProductoService {
    List<TipoProductoDTO> listar();
    TipoProductoDTO obtenerPorId(Long id);
    TipoProductoDTO crear(TipoProductoDTO dto);
    TipoProductoDTO actualizar(Long id, TipoProductoDTO dto);
    void eliminar(Long id);
}