package com.proj.backend.service.impl;

import com.proj.backend.dto.ClienteDTO;
import com.proj.backend.model.Cliente;
import com.proj.backend.repository.ClienteRepository;
import com.proj.backend.service.ClienteService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream().map(ClienteDTO::new).toList();
    }

    @Override
    public List<ClienteDTO> filtrar(String razonSocial, String cuit, String direccion, String email, String telefono, Boolean activo, LocalDate fechaDesde, LocalDate fechaHasta) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> root = cq.from(Cliente.class);

        List<Predicate> predicates = new ArrayList<>();

        if (razonSocial != null && !razonSocial.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + razonSocial.toLowerCase() + "%"));
        }
        if (cuit != null && !cuit.isBlank()) {
            predicates.add(cb.like(root.get("cuit"), "%" + cuit + "%"));
        }
        if (direccion != null && !direccion.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + direccion.toLowerCase() + "%"));
        }
        if (email != null && !email.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (telefono != null && !telefono.isBlank()) {
            predicates.add(cb.like(root.get("telefono"), "%" + telefono + "%"));
        }
        if (activo != null) {
            predicates.add(cb.equal(root.get("activo"), activo));
        }
        if (fechaDesde != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fechaAlta"), fechaDesde.atStartOfDay()));
        }
        if (fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("fechaAlta"), fechaHasta.atTime(23, 59, 59)));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList().stream().map(ClienteDTO::new).toList();
    }

    @Override
    public Optional<ClienteDTO> obtener(Long id) {
        return clienteRepository.findById(id).map(ClienteDTO::new);
    }

    @Override
    public ClienteDTO crear(ClienteDTO dto) {
        if (clienteRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("Ya existe un cliente con ese CUIT");
        }

        Cliente cliente = Cliente.builder()
                .razonSocial(dto.getRazonSocial())
                .cuit(dto.getCuit())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .build();

        return new ClienteDTO(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getCuit().equals(dto.getCuit()) && clienteRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("Ya existe otro cliente con ese CUIT");
        }

        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setCuit(dto.getCuit());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setActivo(dto.getActivo());
        cliente.setLatitud(dto.getLatitud());
        cliente.setLongitud(dto.getLongitud());

        return new ClienteDTO(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}