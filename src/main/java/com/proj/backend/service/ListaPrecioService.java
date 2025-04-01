
package com.proj.backend.service;

import com.proj.backend.dto.ListaPrecioDTO;
import com.proj.backend.model.TipoListaPrecio;

import java.time.LocalDate;
import java.util.List;

public interface ListaPrecioService {
    List<ListaPrecioDTO> listar();
    ListaPrecioDTO crear(ListaPrecioDTO dto);
    ListaPrecioDTO actualizar(Long id, ListaPrecioDTO dto);
    ListaPrecioDTO obtenerPorId(Long id);
    void eliminar(Long id);
    List<ListaPrecioDTO> listarPorTipo(TipoListaPrecio tipo);
    List<ListaPrecioDTO> listarPorFechas(LocalDate desde, LocalDate hasta);
    List<ListaPrecioDTO> listarPorProveedor(Long proveedorId);
}
