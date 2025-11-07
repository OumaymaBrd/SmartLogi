package org.example.smartspring.repository;

import org.example.smartspring.entities.Destinataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinataireRepository extends JpaRepository<Destinataire, String> {
    Optional<Destinataire> findByTelephone(String telephone);

    List<Destinataire> findByVille(String ville);

    @Query("SELECT d FROM Destinataire d WHERE LOWER(d.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(d.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(d.telephone) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(d.ville) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Destinataire> rechercherDestinataires(String keyword);
}
