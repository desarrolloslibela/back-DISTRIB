package com.proj.backend.controller;

import com.proj.backend.dto.PrecioItemVentaDTO;
import com.proj.backend.service.PrecioItemVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios-venta")
@RequiredArgsConstructor
public class PrecioItemVentaController {

    private final PrecioItemVentaService service;

    @GetMapping("/lista/{listaId}")
    public ResponseEntity<List<PrecioItemVentaDTO>> listarPorLista(@PathVariable Long listaId) {
        return ResponseEntity.ok(service.listarPorLista(listaId));
    }

    @PostMapping
    public ResponseEntity<PrecioItemVentaDTO> crear(@RequestBody PrecioItemVentaDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecioItemVentaDTO> actualizar(@PathVariable Long id, @RequestBody PrecioItemVentaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}