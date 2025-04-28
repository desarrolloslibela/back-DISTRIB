package com.proj.backend.service;

import com.proj.backend.dto.ClienteProductoMovimientoDTO;

import java.util.List;

public interface ClienteProductoMovimientoService {

    List<ClienteProductoMovimientoDTO> listarPorCliente(Long clienteId);

    ClienteProductoMovimientoDTO crear(Long clienteId, ClienteProductoMovimientoDTO dto);

    ClienteProductoMovimientoDTO actualizar(Long clienteId, Long movimientoId, ClienteProductoMovimientoDTO dto);

    void eliminar(Long clienteId, Long movimientoId);
}