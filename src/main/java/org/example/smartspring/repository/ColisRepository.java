package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisRepository extends JpaRepository<Colis, String> {
}
