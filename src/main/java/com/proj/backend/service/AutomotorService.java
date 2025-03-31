package com.proj.backend.service;

import com.proj.backend.dto.AutomotorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface AutomotorService {
    Page<AutomotorDTO> buscarConFiltros(String patente, String marca, String modelo, Boolean activo,
                                        LocalDate fechaDesde, LocalDate fechaHasta, Pageable pageable);
    Optional<AutomotorDTO> obtener(Long id);
    AutomotorDTO crear(AutomotorDTO dto);
    AutomotorDTO actualizar(Long id, AutomotorDTO dto);
    void eliminar(Long id);
}
