package org.example.smartspring.repository;

import org.example.smartspring.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByCodePostal(String codePostal);

    boolean existsByCodePostal(String codePostal);
}
