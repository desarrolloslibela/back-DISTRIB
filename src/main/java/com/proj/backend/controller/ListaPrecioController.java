
package com.proj.backend.controller;

import com.proj.backend.dto.ListaPrecioDTO;
import com.proj.backend.model.TipoListaPrecio;
import com.proj.backend.service.ListaPrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/listas-precio")
@RequiredArgsConstructor
public class ListaPrecioController {

    private final ListaPrecioService service;

    @GetMapping
    public ResponseEntity<List<ListaPrecioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaPrecioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ListaPrecioDTO>> listarPorTipo(@PathVariable TipoListaPrecio tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<ListaPrecioDTO>> listarPorProveedor(@PathVariable Long proveedorId) {
        return ResponseEntity.ok(service.listarPorProveedor(proveedorId));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<ListaPrecioDTO>> listarPorFechas(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(service.listarPorFechas(desde, hasta));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ListaPrecioDTO>> filtrarCombinado(
            @RequestParam TipoListaPrecio tipo,
            @RequestParam(required = false) Long proveedorId,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta) {
        return ResponseEntity.ok(service.filtrarCombinado(tipo, proveedorId, desde, hasta));
    }

    @PostMapping
    public ResponseEntity<ListaPrecioDTO> crear(@RequestBody ListaPrecioDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaPrecioDTO> actualizar(@PathVariable Long id, @RequestBody ListaPrecioDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
