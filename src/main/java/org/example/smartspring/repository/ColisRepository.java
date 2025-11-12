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
public interface ColisRepository extends JpaRepository<Colis, String> {

    Optional<Colis> findByNumeroColis(String numeroColis);

    Optional<Colis> findByNumeroSuivi(String numeroSuivi);

    List<Colis> findByClientExpediteur_Id(String clientExpediteurId);

    List<Colis> findByZone_Id(String zoneId);

    List<Colis> findByDestinataire_Id(String destinataireId);

    List<Colis> findByLivreur_IdOrLivreurLivree_Id(String livreurId1, String livreurId2);


    @Query("SELECT c.livreur.id FROM Colis c WHERE c.numeroColis = :numeroColis")
    String findLivreurIdByNumeroColis(@Param("numeroColis") String numeroColis);

    List<Colis> findByLivreur_Id(String livreurId);



}
