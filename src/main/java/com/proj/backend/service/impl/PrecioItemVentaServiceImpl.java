package com.proj.backend.service.impl;

import com.proj.backend.dto.PrecioItemVentaDTO;
import com.proj.backend.model.ListaPrecioVenta;
import com.proj.backend.model.PrecioItemVenta;
import com.proj.backend.model.Producto;
import com.proj.backend.repository.ListaPrecioVentaRepository;
import com.proj.backend.repository.PrecioItemVentaRepository;
import com.proj.backend.repository.ProductoRepository;
import com.proj.backend.service.PrecioItemVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioItemVentaServiceImpl implements PrecioItemVentaService {

    private final PrecioItemVentaRepository itemRepo;
    private final ListaPrecioVentaRepository listaRepo;
    private final ProductoRepository productoRepo;

    @Override
    public List<PrecioItemVentaDTO> listarPorLista(Long listaId) {
        return itemRepo.findByListaId(listaId).stream()
                .map(PrecioItemVentaDTO::new)
                .toList();
    }

    @Override
    public PrecioItemVentaDTO crear(PrecioItemVentaDTO dto) {
        ListaPrecioVenta lista = listaRepo.findById(dto.getListaId())
                .orElseThrow(() -> new RuntimeException("La lista de precios de venta no existe."));

        Producto producto = productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("El producto no existe."));
        if (Boolean.FALSE.equals(producto.getActivo())) {
            throw new RuntimeException("El producto no está activo.");
        }

        boolean yaExiste = itemRepo.findByListaId(dto.getListaId()).stream()
                .anyMatch(i -> i.getProducto().getId().equals(dto.getProductoId()));
        if (yaExiste) {
            throw new RuntimeException("Este producto ya está cargado en la lista.");
        }

        if (dto.getPrecioFinal() == null || dto.getPrecioFinal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precio final debe ser mayor a cero.");
        }

        if (dto.getAlicuotaIVA() == null) {
            throw new RuntimeException("Debe seleccionar una alícuota de IVA válida.");
        }

        PrecioItemVenta item = PrecioItemVenta.builder()
                .lista(lista)
                .producto(producto)
                .precioFinal(dto.getPrecioFinal())
                .alicuotaIVA(dto.getAlicuotaIVA())
                .build();

        return new PrecioItemVentaDTO(itemRepo.save(item));
    }

    @Override
    public PrecioItemVentaDTO actualizar(Long id, PrecioItemVentaDTO dto) {
        PrecioItemVenta item = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado."));

        if (dto.getPrecioFinal() == null || dto.getPrecioFinal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precio final debe ser mayor a cero.");
        }

        if (dto.getAlicuotaIVA() == null) {
            throw new RuntimeException("Debe seleccionar una alícuota de IVA válida.");
        }

        item.setPrecioFinal(dto.getPrecioFinal());
        item.setAlicuotaIVA(dto.getAlicuotaIVA());

        return new PrecioItemVentaDTO(itemRepo.save(item));
    }

    @Override
    public void eliminar(Long id) {
        if (!itemRepo.existsById(id)) {
            throw new RuntimeException("Ítem no encontrado.");
        }
        itemRepo.deleteById(id);
    }
}