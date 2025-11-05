package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColisRepository extends JpaRepository<Colis, String> , JpaSpecificationExecutor<Colis> {

    Optional<Colis> findByNumeroSuivi(String numeroSuivi);

    @Query("SELECT c FROM Colis c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.villeDestination) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Colis> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Colis> findByStatut(StatutColis statut);

    List<Colis> findByPriorite(PrioriteColis priorite);

    List<Colis> findByLivreurId(String livreurId);

    List<Colis> findByZoneId(String zoneId);

    @Query("SELECT COUNT(c), SUM(c.poids) FROM Colis c WHERE c.livreur.id = :livreurId")
    Object[] getStatsByLivreur(@Param("livreurId") String livreurId);

    @Query("SELECT COUNT(c), SUM(c.poids) FROM Colis c WHERE c.zone.id = :zoneId")
    Object[] getStatsByZone(@Param("zoneId") String zoneId);
}