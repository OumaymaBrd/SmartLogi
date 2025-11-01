package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {

    Page<Colis> findByStatut(StatutColis statut, Pageable pageable);

    Page<Colis> findByPriorite(PrioriteColis priorite, Pageable pageable);

    Page<Colis> findByVilleDestination(String ville, Pageable pageable);

    Page<Colis> findByZoneId(Long zoneId, Pageable pageable);

    List<Colis> findByLivreurId(Long livreurId);

    List<Colis> findByClientExpediteurId(Long clientId);

    List<Colis> findByDestinataireId(Long destinataireId);

    @Query("SELECT c FROM Colis c WHERE c.statut = :statut AND c.priorite = :priorite")
    Page<Colis> findByStatutAndPriorite(@Param("statut") StatutColis statut,
                                        @Param("priorite") PrioriteColis priorite,
                                        Pageable pageable);

    @Query("SELECT COUNT(c) FROM Colis c WHERE c.livreur.id = :livreurId")
    Long countByLivreurId(@Param("livreurId") Long livreurId);

    @Query("SELECT SUM(c.poids) FROM Colis c WHERE c.livreur.id = :livreurId")
    BigDecimal sumPoidsByLivreurId(@Param("livreurId") Long livreurId);

    @Query("SELECT COUNT(c) FROM Colis c WHERE c.zone.id = :zoneId")
    Long countByZoneId(@Param("zoneId") Long zoneId);

    @Query("SELECT SUM(c.poids) FROM Colis c WHERE c.zone.id = :zoneId")
    BigDecimal sumPoidsByZoneId(@Param("zoneId") Long zoneId);

    @Query("SELECT c FROM Colis c WHERE " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.villeDestination) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Colis> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
