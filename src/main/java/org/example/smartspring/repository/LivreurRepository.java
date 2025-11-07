package org.example.smartspring.repository;

import org.example.smartspring.entities.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, String> {

    Optional<Livreur> findByTelephone(String telephone);

    List<Livreur> findByActif(Boolean actif);

    @Query("SELECT l FROM Livreur l WHERE l.actif = true")
    List<Livreur> findAllActifs();

    @Query("SELECT l FROM Livreur l WHERE LOWER(l.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.telephone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Livreur> rechercherLivreurs(String keyword);

    @Query("SELECT DISTINCT l FROM Livreur l JOIN l.colis c JOIN c.zone z WHERE z.nom = :zoneNom")
    List<Livreur> findByZoneNom(String zoneNom);
}
