package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisRepository extends JpaRepository<Colis, String> {
    List<Colis> findByDescriptionIgnoreCase(String description);
}
