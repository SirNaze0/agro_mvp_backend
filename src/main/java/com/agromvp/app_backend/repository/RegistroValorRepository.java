package com.agromvp.app_backend.repository;
import com.agromvp.app_backend.entity.RegistroValor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegistroValorRepository extends JpaRepository<RegistroValor, Long> {

    Optional<RegistroValor> findByClientUuid(UUID clientUuid);
}