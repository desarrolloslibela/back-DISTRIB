package com.proj.backend.repository;

import com.proj.backend.model.Automotor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomotorRepository extends JpaRepository<Automotor, Long> {
    boolean existsByPatente(String patente);
}