package com.proj.backend.service;

import com.proj.backend.dto.ProveedorDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProveedorService {

    List<ProveedorDTO> listar();

    List<ProveedorDTO> filtrar(String razonSocial, String cuit, String direccion, String email, String telefono, Boolean activo, LocalDate fechaDesde, LocalDate fechaHasta);

    Optional<ProveedorDTO> obtener(Long id);

    ProveedorDTO crear(ProveedorDTO dto);

    ProveedorDTO actualizar(Long id, ProveedorDTO dto);

    void eliminar(Long id);
}