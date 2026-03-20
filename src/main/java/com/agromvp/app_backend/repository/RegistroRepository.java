package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Optional<Registro> findByClientUuid(UUID clientUuid);
}