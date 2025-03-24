package com.proj.backend.controller;

import com.proj.backend.dto.TipoProductoDTO;
import com.proj.backend.service.TipoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
@RequiredArgsConstructor
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> listar() {
        return ResponseEntity.ok(tipoProductoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProductoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoProductoDTO> crear(@RequestBody TipoProductoDTO dto) {
        return ResponseEntity.ok(tipoProductoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> actualizar(@PathVariable Long id, @RequestBody TipoProductoDTO dto) {
        return ResponseEntity.ok(tipoProductoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}