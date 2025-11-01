package org.example.smartspring.repository;

import org.example.smartspring.entities.Destinataire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinataireRepository extends JpaRepository<Destinataire, Long> {

    @Query("SELECT d FROM Destinataire d WHERE " +
            "LOWER(d.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.telephone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Destinataire> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
