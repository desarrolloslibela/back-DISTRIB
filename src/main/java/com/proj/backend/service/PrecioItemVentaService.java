package com.proj.backend.service;

import com.proj.backend.dto.PrecioItemVentaDTO;
import java.util.List;

public interface PrecioItemVentaService {
    List<PrecioItemVentaDTO> listarPorLista(Long listaId);
    PrecioItemVentaDTO crear(PrecioItemVentaDTO dto);
    PrecioItemVentaDTO actualizar(Long id, PrecioItemVentaDTO dto);
    void eliminar(Long id);
}