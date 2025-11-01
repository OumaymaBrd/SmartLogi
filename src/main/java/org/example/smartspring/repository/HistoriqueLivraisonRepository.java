package org.example.smartspring.repository;

import org.example.smartspring.entities.HistoriqueLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueLivraisonRepository extends JpaRepository<HistoriqueLivraison, Long> {

    List<HistoriqueLivraison> findByColisIdOrderByDateChangementDesc(Long colisId);
}
