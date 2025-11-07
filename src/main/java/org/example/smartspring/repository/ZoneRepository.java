package org.example.smartspring.repository;

import org.example.smartspring.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {
    Optional<Zone> findByNom(String nom);

    Optional<Zone> findByCodePostal(String codePostal);

    @Query("SELECT z FROM Zone z WHERE LOWER(z.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(z.codePostal) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Zone> rechercherZones(String keyword);
}
