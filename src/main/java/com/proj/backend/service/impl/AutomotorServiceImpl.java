package com.proj.backend.service.impl;

import com.proj.backend.dto.AutomotorDTO;
import com.proj.backend.model.Automotor;
import com.proj.backend.repository.AutomotorRepository;
import com.proj.backend.service.AutomotorService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AutomotorServiceImpl implements AutomotorService {

    private final AutomotorRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AutomotorDTO> buscarConFiltros(String patente, String marca, String modelo, Boolean activo,
                                               LocalDate fechaDesde, LocalDate fechaHasta, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Automotor> cq = cb.createQuery(Automotor.class);
        Root<Automotor> root = cq.from(Automotor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (patente != null && !patente.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("patente")), "%" + patente.toLowerCase() + "%"));
        }
        if (marca != null && !marca.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%"));
        }
        if (modelo != null && !modelo.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%"));
        }
        if (activo != null) {
            predicates.add(cb.equal(root.get("activo"), activo));
        }
        if (fechaDesde != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fechaAlta"), fechaDesde));
        }
        if (fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("fechaAlta"), fechaHasta));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<Automotor> resultados = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Automotor> countRoot = countQuery.from(Automotor.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultados.stream().map(AutomotorDTO::new).toList(), pageable, total);
    }

    @Override
    public Optional<AutomotorDTO> obtener(Long id) {
        return repo.findById(id).map(AutomotorDTO::new);
    }

    @Override
    public AutomotorDTO crear(AutomotorDTO dto) {
        if (repo.existsByPatente(dto.getPatente())) {
            throw new RuntimeException("Ya existe un automotor con esa patente.");
        }

        Automotor auto = Automotor.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .observaciones(dto.getObservaciones())
                .build();

        return new AutomotorDTO(repo.save(auto));
    }

    @Override
    public AutomotorDTO actualizar(Long id, AutomotorDTO dto) {
        Automotor auto = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));

        if (!auto.getPatente().equals(dto.getPatente()) && repo.existsByPatente(dto.getPatente())) {
            throw new RuntimeException("Ya existe otro automotor con esa patente.");
        }

        auto.setPatente(dto.getPatente());
        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setActivo(dto.getActivo());
        auto.setObservaciones(dto.getObservaciones());

        return new AutomotorDTO(repo.save(auto));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
