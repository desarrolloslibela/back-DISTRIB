package com.proj.backend.service;

import com.proj.backend.dto.PrecioItemDTO;

import java.util.List;

public interface PrecioItemService {
    List<PrecioItemDTO> listarPorLista(Long listaId);
    PrecioItemDTO crear(PrecioItemDTO dto);
}