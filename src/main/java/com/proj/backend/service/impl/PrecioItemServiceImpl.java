package com.proj.backend.service.impl;

import com.proj.backend.dto.PrecioItemDTO;
import com.proj.backend.model.ListaPrecio;
import com.proj.backend.model.PrecioItem;
import com.proj.backend.model.Producto;
import com.proj.backend.repository.ListaPrecioRepository;
import com.proj.backend.repository.PrecioItemRepository;
import com.proj.backend.repository.ProductoRepository;
import com.proj.backend.service.PrecioItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioItemServiceImpl implements PrecioItemService {

    private final PrecioItemRepository itemRepo;
    private final ListaPrecioRepository listaRepo;
    private final ProductoRepository productoRepo;

    @Override
    public List<PrecioItemDTO> listarPorLista(Long listaId) {
        return itemRepo.findAll().stream()
                .filter(i -> i.getListaPrecio().getId().equals(listaId))
                .map(PrecioItemDTO::new)
                .toList();
    }

    @Override
    public PrecioItemDTO crear(PrecioItemDTO dto) {
        ListaPrecio lista = listaRepo.findById(dto.getListaPrecioId())
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        Producto producto = productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (itemRepo.existsByListaPrecioAndProducto(lista, producto)) {
            throw new RuntimeException("Ya existe un precio para este producto en esta lista");
        }

        PrecioItem item = PrecioItem.builder()
                .listaPrecio(lista)
                .producto(producto)
                .precioNeto(dto.getPrecioNeto())
                .porcentajeIVA(dto.getPorcentajeIVA())
                .build();

        return new PrecioItemDTO(itemRepo.save(item));
    }
}