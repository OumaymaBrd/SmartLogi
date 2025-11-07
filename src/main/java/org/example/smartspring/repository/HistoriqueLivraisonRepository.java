package org.example.smartspring.repository;

import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.enums.StatutColis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueLivraisonRepository extends JpaRepository<HistoriqueLivraison, String> {

//    List<HistoriqueLivraison> findByColisIdOrderByDateChangementDesc(String colisId);

//    List<HistoriqueLivraison> findByStatut(StatutColis statut);
//
//    @Query("SELECT h FROM HistoriqueLivraison h WHERE h.dateChangement BETWEEN :dateDebut AND :dateFin ORDER BY h.dateChangement DESC")
//    List<HistoriqueLivraison> findByDateChangementBetween(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);
//
//    @Query("SELECT h FROM HistoriqueLivraison h WHERE h.dateChangement BETWEEN :dateDebut AND :dateFin ORDER BY h.dateChangement DESC")
//    List<HistoriqueLivraison> findByPeriode(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);
}
