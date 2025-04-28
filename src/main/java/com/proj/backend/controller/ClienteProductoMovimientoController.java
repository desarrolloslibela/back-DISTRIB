package com.proj.backend.controller;

import com.proj.backend.dto.ClienteProductoMovimientoDTO;
import com.proj.backend.service.ClienteProductoMovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/movimientos")
@RequiredArgsConstructor
public class ClienteProductoMovimientoController {

    private final ClienteProductoMovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<ClienteProductoMovimientoDTO>> listar(@PathVariable Long clienteId) {
        return ResponseEntity.ok(movimientoService.listarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<ClienteProductoMovimientoDTO> crear(@PathVariable Long clienteId, @RequestBody ClienteProductoMovimientoDTO dto) {
        return ResponseEntity.ok(movimientoService.crear(clienteId, dto));
    }

    @PutMapping("/{movimientoId}")
    public ResponseEntity<ClienteProductoMovimientoDTO> actualizar(@PathVariable Long clienteId, @PathVariable Long movimientoId, @RequestBody ClienteProductoMovimientoDTO dto) {
        return ResponseEntity.ok(movimientoService.actualizar(clienteId, movimientoId, dto));
    }

    @DeleteMapping("/{movimientoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long clienteId, @PathVariable Long movimientoId) {
        movimientoService.eliminar(clienteId, movimientoId);
        return ResponseEntity.noContent().build();
    }
}