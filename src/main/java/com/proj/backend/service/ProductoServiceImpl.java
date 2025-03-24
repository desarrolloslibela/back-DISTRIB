package com.proj.backend.service;

import com.proj.backend.dto.ProductoDTO;
import com.proj.backend.model.Producto;
import com.proj.backend.model.TipoProducto;
import com.proj.backend.repository.ProductoRepository;
import com.proj.backend.repository.TipoProductoRepository;
import com.proj.backend.specification.ProductoSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductoDTO> buscarConFiltros(String nombre, Long tipoProductoId, Boolean activo, Pageable pageable) {
        return productoRepository.findAll(
                ProductoSpecification.filtrar(nombre, tipoProductoId, activo), pageable
        ).map(producto -> modelMapper.map(producto, ProductoDTO.class));
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = modelMapper.map(dto, Producto.class);
        TipoProducto tipo = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));
        producto.setTipoProducto(tipo);
        return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        existente.setNombre(dto.getNombre());
        existente.setCapacidad(dto.getCapacidad());
        existente.setActivo(dto.getActivo());

        TipoProducto tipo = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));
        existente.setTipoProducto(tipo);

        return modelMapper.map(productoRepository.save(existente), ProductoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Optional<ProductoDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(producto -> modelMapper.map(producto, ProductoDTO.class));
    }
}