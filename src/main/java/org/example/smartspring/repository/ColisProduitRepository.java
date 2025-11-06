package org.example.smartspring.repository;

import org.example.smartspring.entities.ColisProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisProduitRepository extends JpaRepository<ColisProduit, String> {

    List<ColisProduit> findByColis_Id(String colisId);

    List<ColisProduit> findByProduit_Id(String produitId);
}
