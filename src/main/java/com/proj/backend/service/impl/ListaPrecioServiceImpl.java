
package com.proj.backend.service.impl;

import com.proj.backend.dto.ListaPrecioDTO;
import com.proj.backend.model.ListaPrecio;
import com.proj.backend.model.TipoListaPrecio;
import com.proj.backend.repository.ListaPrecioRepository;
import com.proj.backend.repository.ProveedorRepository;
import com.proj.backend.service.ListaPrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaPrecioServiceImpl implements ListaPrecioService {

    private final ListaPrecioRepository listaRepo;
    private final ProveedorRepository proveedorRepo;

    @Override
    public List<ListaPrecioDTO> listar() {
        return listaRepo.findAll().stream().map(ListaPrecioDTO::new).toList();
    }

    @Override
    public ListaPrecioDTO crear(ListaPrecioDTO dto) {
        if (dto.getProveedorId() != null && dto.getTipo().name().equals("COMPRA")) {
            if (listaRepo.existsByTipoAndProveedorAndFechaDesde(dto.getTipo(),
                    proveedorRepo.findById(dto.getProveedorId()).orElseThrow(),
                    dto.getFechaDesde())) {
                throw new RuntimeException("Ya existe una lista de ese proveedor para esa fecha");
            }
        }

        ListaPrecio lista = ListaPrecio.builder()
                .tipo(dto.getTipo())
                .fechaDesde(dto.getFechaDesde())
                .fechaHasta(dto.getFechaHasta())
                .observaciones(dto.getObservaciones())
                .proveedor(dto.getProveedorId() != null
                        ? proveedorRepo.findById(dto.getProveedorId()).orElse(null)
                        : null)
                .build();

        return new ListaPrecioDTO(listaRepo.save(lista));
    }

    @Override
    public ListaPrecioDTO actualizar(Long id, ListaPrecioDTO dto) {
        ListaPrecio lista = listaRepo.findById(id).orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        lista.setTipo(dto.getTipo());
        lista.setFechaDesde(dto.getFechaDesde());
        lista.setFechaHasta(dto.getFechaHasta());
        lista.setObservaciones(dto.getObservaciones());
        lista.setProveedor(dto.getProveedorId() != null
                ? proveedorRepo.findById(dto.getProveedorId()).orElse(null)
                : null);

        return new ListaPrecioDTO(listaRepo.save(lista));
    }

    @Override
    public ListaPrecioDTO obtenerPorId(Long id) {
        return new ListaPrecioDTO(listaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada")));
    }

    @Override
    public void eliminar(Long id) {
        if (!listaRepo.existsById(id)) {
            throw new RuntimeException("Lista no encontrada");
        }
        listaRepo.deleteById(id);
    }

    @Override
    public List<ListaPrecioDTO> listarPorTipo(TipoListaPrecio tipo) {
        return listaRepo.findByTipo(tipo).stream().map(ListaPrecioDTO::new).toList();
    }

    @Override
    public List<ListaPrecioDTO> listarPorFechas(LocalDate desde, LocalDate hasta) {
        return listaRepo.findByFechaDesdeGreaterThanEqualAndFechaHastaLessThanEqual(desde, hasta)
                .stream().map(ListaPrecioDTO::new).toList();
    }

    @Override
    public List<ListaPrecioDTO> listarPorProveedor(Long proveedorId) {
        return listaRepo.findByProveedorId(proveedorId).stream()
                .map(ListaPrecioDTO::new).toList();
    }
}
