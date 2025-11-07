package org.example.smartspring.repository;

import org.example.smartspring.entities.ClientExpediteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientExpediteurRepository extends JpaRepository<ClientExpediteur, String> {

    Optional<ClientExpediteur> findByTelephone(String telephone);

    Optional<ClientExpediteur> findByEmail(String email);

    List<ClientExpediteur> findByVille(String ville);

    @Query("SELECT c FROM ClientExpediteur c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.telephone) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.ville) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ClientExpediteur> rechercherClients(String keyword);
}
