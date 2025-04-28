package com.proj.backend.service;

import com.proj.backend.dto.ClienteDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteDTO> listar();

    List<ClienteDTO> filtrar(String razonSocial, String cuit, String direccion, String email, String telefono, Boolean activo, LocalDate fechaDesde, LocalDate fechaHasta);

    Optional<ClienteDTO> obtener(Long id);

    ClienteDTO crear(ClienteDTO dto);

    ClienteDTO actualizar(Long id, ClienteDTO dto);

    void eliminar(Long id);
}