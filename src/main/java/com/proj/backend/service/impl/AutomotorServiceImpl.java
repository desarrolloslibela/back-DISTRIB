package com.proj.backend.service.impl;

import com.proj.backend.dto.AutomotorDTO;
import com.proj.backend.model.Automotor;
import com.proj.backend.repository.AutomotorRepository;
import com.proj.backend.service.AutomotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutomotorServiceImpl implements AutomotorService {

    private final AutomotorRepository repo;

    @Override
    public List<AutomotorDTO> listar() {
        return repo.findAll().stream().map(AutomotorDTO::new).toList();
    }

    @Override
    public Optional<AutomotorDTO> obtener(Long id) {
        return repo.findById(id).map(AutomotorDTO::new);
    }

    @Override
    public AutomotorDTO crear(AutomotorDTO dto) {
        if (repo.existsByPatente(dto.getPatente())) {
            throw new RuntimeException("Ya existe un automotor con esa patente.");
        }

        Automotor auto = Automotor.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .observaciones(dto.getObservaciones())
                .build();

        return new AutomotorDTO(repo.save(auto));
    }

    @Override
    public AutomotorDTO actualizar(Long id, AutomotorDTO dto) {
        Automotor auto = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));

        if (!auto.getPatente().equals(dto.getPatente()) && repo.existsByPatente(dto.getPatente())) {
            throw new RuntimeException("Ya existe otro automotor con esa patente.");
        }

        auto.setPatente(dto.getPatente());
        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setActivo(dto.getActivo());
        auto.setObservaciones(dto.getObservaciones());

        return new AutomotorDTO(repo.save(auto));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}