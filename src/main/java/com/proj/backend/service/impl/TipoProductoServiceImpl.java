package com.proj.backend.service.impl;

import com.proj.backend.dto.TipoProductoDTO;
import com.proj.backend.model.TipoProducto;
import com.proj.backend.repository.TipoProductoRepository;
import com.proj.backend.service.TipoProductoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TipoProductoDTO> listar() {
        return tipoProductoRepository.findAll().stream()
                .map(tp -> modelMapper.map(tp, TipoProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoProductoDTO obtenerPorId(Long id) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));
        return modelMapper.map(tipoProducto, TipoProductoDTO.class);
    }

    @Override
    public TipoProductoDTO crear(TipoProductoDTO dto) {
        TipoProducto tipo = modelMapper.map(dto, TipoProducto.class);
        return modelMapper.map(tipoProductoRepository.save(tipo), TipoProductoDTO.class);
    }

    @Override
    public TipoProductoDTO actualizar(Long id, TipoProductoDTO dto) {
        TipoProducto existente = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));
        existente.setNombre(dto.getNombre());
        return modelMapper.map(tipoProductoRepository.save(existente), TipoProductoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        tipoProductoRepository.deleteById(id);
    }
}