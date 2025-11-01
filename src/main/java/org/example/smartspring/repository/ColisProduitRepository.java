package org.example.smartspring.repository;

import org.example.smartspring.entities.ColisProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisProduitRepository extends JpaRepository<ColisProduit, Long> {

    List<ColisProduit> findByColisId(Long colisId);

    List<ColisProduit> findByProduitId(Long produitId);
}
