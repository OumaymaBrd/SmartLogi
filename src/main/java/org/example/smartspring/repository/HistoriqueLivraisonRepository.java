package org.example.smartspring.repository;

import org.example.smartspring.entities.HistoriqueLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistoriqueLivraisonRepository extends JpaRepository<HistoriqueLivraison, String> {

    @Query("SELECT h FROM HistoriqueLivraison h WHERE h.colisId = :colisId ORDER BY h.dateChangement DESC")
    List<HistoriqueLivraison> findByColisIdOrderByDateChangementDesc(@Param("colisId") String colisId);

}

