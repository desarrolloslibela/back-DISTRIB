package com.proj.backend.controller;

import com.proj.backend.dto.PrecioItemDTO;
import com.proj.backend.service.PrecioItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
@RequiredArgsConstructor
public class PrecioItemController {

    private final PrecioItemService service;

    @GetMapping("/lista/{listaId}")
    public ResponseEntity<List<PrecioItemDTO>> listarPorLista(@PathVariable Long listaId) {
        return ResponseEntity.ok(service.listarPorLista(listaId));
    }

    @PostMapping
    public ResponseEntity<PrecioItemDTO> crear(@RequestBody PrecioItemDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }
}