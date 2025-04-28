package com.proj.backend.service.impl;

import com.proj.backend.dto.ClienteProductoMovimientoDTO;
import com.proj.backend.model.Cliente;
import com.proj.backend.model.ClienteProductoMovimiento;
import com.proj.backend.model.MovimientoTipo;
import com.proj.backend.model.Producto;
import com.proj.backend.repository.ClienteProductoMovimientoRepository;
import com.proj.backend.repository.ClienteRepository;
import com.proj.backend.repository.ProductoRepository;
import com.proj.backend.service.ClienteProductoMovimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteProductoMovimientoServiceImpl implements ClienteProductoMovimientoService {

    private final ClienteProductoMovimientoRepository movimientoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<ClienteProductoMovimientoDTO> listarPorCliente(Long clienteId) {
        return movimientoRepository.findByClienteId(clienteId)
                .stream()
                .map(ClienteProductoMovimientoDTO::new)
                .toList();
    }

    @Override
    public ClienteProductoMovimientoDTO crear(Long clienteId, ClienteProductoMovimientoDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        ClienteProductoMovimiento movimiento = ClienteProductoMovimiento.builder()
                .cliente(cliente)
                .producto(producto)
                .fechaMovimiento(dto.getFechaMovimiento() != null ? dto.getFechaMovimiento() : LocalDateTime.now())
                .tipoMovimiento(dto.getTipoMovimiento())
                .cantidad(dto.getCantidad())
                .observacion(dto.getObservacion())
                .build();

        return new ClienteProductoMovimientoDTO(movimientoRepository.save(movimiento));
    }

    @Override
    public ClienteProductoMovimientoDTO actualizar(Long clienteId, Long movimientoId, ClienteProductoMovimientoDTO dto) {
        ClienteProductoMovimiento movimiento = movimientoRepository.findById(movimientoId)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        if (!movimiento.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException("El movimiento no pertenece al cliente indicado");
        }

        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setObservacion(dto.getObservacion());

        return new ClienteProductoMovimientoDTO(movimientoRepository.save(movimiento));
    }

    @Override
    public void eliminar(Long clienteId, Long movimientoId) {
        ClienteProductoMovimiento movimiento = movimientoRepository.findById(movimientoId)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        if (!movimiento.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException("El movimiento no pertenece al cliente indicado");
        }

        movimientoRepository.delete(movimiento);
    }
}