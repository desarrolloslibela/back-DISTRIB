package com.proj.backend.repository;

import com.proj.backend.model.ClienteProductoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteProductoMovimientoRepository extends JpaRepository<ClienteProductoMovimiento, Long> {
    List<ClienteProductoMovimiento> findByClienteId(Long clienteId);
}