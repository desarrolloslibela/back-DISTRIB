package com.proj.backend.controller;

import com.proj.backend.dto.ListaPrecioVentaDTO;
import com.proj.backend.service.ListaPrecioVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/listas-precio-venta")
@RequiredArgsConstructor
public class ListaPrecioVentaController {

    private final ListaPrecioVentaService service;

    @PostMapping
    public ResponseEntity<ListaPrecioVentaDTO> crear(@RequestBody ListaPrecioVentaDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaPrecioVentaDTO> actualizar(@PathVariable Long id, @RequestBody ListaPrecioVentaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ListaPrecioVentaDTO>> filtrar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Pageable pageable) {
        return ResponseEntity.ok(service.filtrar(desde, hasta, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaPrecioVentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}