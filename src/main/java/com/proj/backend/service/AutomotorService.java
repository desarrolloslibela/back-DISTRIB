package com.proj.backend.service;

import com.proj.backend.dto.AutomotorDTO;
import java.util.List;
import java.util.Optional;

public interface AutomotorService {
    List<AutomotorDTO> listar();
    Optional<AutomotorDTO> obtener(Long id);
    AutomotorDTO crear(AutomotorDTO dto);
    AutomotorDTO actualizar(Long id, AutomotorDTO dto);
    void eliminar(Long id);
}