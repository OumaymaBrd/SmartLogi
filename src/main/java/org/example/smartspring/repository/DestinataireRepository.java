package org.example.smartspring.repository;

import org.example.smartspring.entities.Destinataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinataireRepository extends JpaRepository<Destinataire, String> {
}
