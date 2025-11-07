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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ColisRepository extends JpaRepository<Colis, String> {

    Optional<Colis> findByNumeroColis(String numeroColis);

    Optional<Colis> findByNumeroSuivi(String numeroSuivi);

    List<Colis> findByClientExpediteur_Id(String clientExpediteurId);

    List<Colis> findByZone_Id(String zoneId);

    List<Colis> findByDestinataire_Id(String destinataireId);

    List<Colis> findByLivreur_Id(String livreurId);


    @Query("SELECT c FROM Colis c WHERE " +
            "(:statut IS NULL OR c.statut = :statut) AND " +
            "(:priorite IS NULL OR c.priorite = :priorite) AND " +
            "(:zoneNom IS NULL OR c.zone.nom LIKE %:zoneNom%) AND " +
            "(:ville IS NULL OR c.destinataire.ville LIKE %:ville%) AND " +
            "(:dateDebut IS NULL OR c.dateCreation >= :dateDebut) AND " +
            "(:dateFin IS NULL OR c.dateCreation <= :dateFin) AND " +
            "(:livreurId IS NULL OR c.livreur.id = :livreurId) AND " +
            "(:motCle IS NULL OR c.numeroColis LIKE %:motCle% OR c.numeroSuivi LIKE %:motCle% OR " +
            "c.clientExpediteur.nom LIKE %:motCle% OR c.destinataire.nom LIKE %:motCle%)")
    Page<Colis> filtrerColis(
            @Param("statut") StatutColis statut,
            @Param("priorite") PrioriteColis priorite,
            @Param("zoneNom") String zoneNom,
            @Param("ville") String ville,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            @Param("livreurId") String livreurId,
            @Param("motCle") String motCle,
            Pageable pageable
    );

    @Query("SELECT COUNT(c) FROM Colis c WHERE c.priorite IN ('URGENTE', 'EXPRESS')")
    Long countColisPrioritaires();

    @Query("SELECT COUNT(c) FROM Colis c WHERE c.dateLivraisonReelle IS NULL AND c.dateCreation < :dateLimit")
    Long countColisEnRetard(@Param("dateLimit") LocalDateTime dateLimit);

    @Query("SELECT c.statut, COUNT(c) FROM Colis c GROUP BY c.statut")
    List<Object[]> countColisParStatut();

    @Query("SELECT c.priorite, COUNT(c) FROM Colis c GROUP BY c.priorite")
    List<Object[]> countColisParPriorite();

    @Query("SELECT c.zone.nom, COUNT(c) FROM Colis c GROUP BY c.zone.nom")
    List<Object[]> countColisParZone();

    @Query("SELECT c.livreur.id, c.livreur.nom, COUNT(c) FROM Colis c " +
            "WHERE c.livreur IS NOT NULL GROUP BY c.livreur.id, c.livreur.nom")
    List<Object[]> statistiquesParLivreur();
}
