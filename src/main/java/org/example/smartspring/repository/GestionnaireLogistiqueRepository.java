package org.example.smartspring.repository;

import org.example.smartspring.entities.GestionnaireLogistique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GestionnaireLogistiqueRepository extends JpaRepository<GestionnaireLogistique, String> {
    Optional<GestionnaireLogistique> findByEmail(String email);
    boolean existsByEmail(String email);
}
