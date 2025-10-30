package org.example.smartspring.repository;

import org.example.smartspring.entities.ClientExpediteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientExpediteurRepository extends JpaRepository<ClientExpediteur, String> {
}
