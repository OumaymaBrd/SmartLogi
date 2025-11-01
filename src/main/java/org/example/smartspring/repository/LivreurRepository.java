package org.example.smartspring.repository;

import org.example.smartspring.entities.Livreur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long> {

    List<Livreur> findByZoneAssigneeId(Long zoneId);

    @Query("SELECT l FROM Livreur l WHERE " +
            "LOWER(l.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.telephone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Livreur> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
