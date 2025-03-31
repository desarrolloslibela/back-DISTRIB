package com.proj.backend.controller;

import com.proj.backend.dto.AutomotorDTO;
import com.proj.backend.service.AutomotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/automotores")
@RequiredArgsConstructor
public class AutomotorController {

    private final AutomotorService service;

    @GetMapping
    public ResponseEntity<Page<AutomotorDTO>> filtrarAutomotores(
            @RequestParam(required = false) String patente,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.buscarConFiltros(patente, marca, modelo, activo, fechaDesde, fechaHasta, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomotorDTO> obtener(@PathVariable Long id) {
        return service.obtener(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AutomotorDTO> crear(@RequestBody AutomotorDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutomotorDTO> actualizar(@PathVariable Long id, @RequestBody AutomotorDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
