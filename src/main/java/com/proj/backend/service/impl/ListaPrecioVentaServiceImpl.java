package com.proj.backend.service.impl;

import com.proj.backend.dto.ListaPrecioVentaDTO;
import com.proj.backend.model.ListaPrecioVenta;
import com.proj.backend.repository.ListaPrecioVentaRepository;
import com.proj.backend.service.ListaPrecioVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaPrecioVentaServiceImpl implements ListaPrecioVentaService {

    private final ListaPrecioVentaRepository repo;

    @Override
    public ListaPrecioVentaDTO crear(ListaPrecioVentaDTO dto) {
        boolean solapada = !repo.findByFechaDesdeLessThanEqualAndFechaHastaGreaterThanEqual(
                dto.getFechaHasta(), dto.getFechaDesde()).isEmpty();

        if (solapada) {
            throw new RuntimeException("Ya existe una lista de precios de venta con fechas superpuestas.");
        }

        ListaPrecioVenta nueva = ListaPrecioVenta.builder()
                .fechaDesde(dto.getFechaDesde())
                .fechaHasta(dto.getFechaHasta())
                .observaciones(dto.getObservaciones())
                .build();

        return new ListaPrecioVentaDTO(repo.save(nueva));
    }

    @Override
    public ListaPrecioVentaDTO actualizar(Long id, ListaPrecioVentaDTO dto) {
        ListaPrecioVenta existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        List<ListaPrecioVenta> solapadas = repo.findByFechaDesdeLessThanEqualAndFechaHastaGreaterThanEqual(
                dto.getFechaHasta(), dto.getFechaDesde());

        boolean haySolapadas = solapadas.stream()
                .anyMatch(l -> !l.getId().equals(id));

        if (haySolapadas) {
            throw new RuntimeException("Ya existe una lista de precios de venta con fechas superpuestas.");
        }

        existente.setFechaDesde(dto.getFechaDesde());
        existente.setFechaHasta(dto.getFechaHasta());
        existente.setObservaciones(dto.getObservaciones());

        return new ListaPrecioVentaDTO(repo.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Lista no encontrada");
        }
        repo.deleteById(id);
    }

    @Override
    public ListaPrecioVentaDTO obtenerPorId(Long id) {
        return new ListaPrecioVentaDTO(repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada")));
    }

    @Override
    public Page<ListaPrecioVentaDTO> filtrar(LocalDate desde, LocalDate hasta, Pageable pageable) {
        return repo.findAll(pageable)
                .map(ListaPrecioVentaDTO::new);
    }
}