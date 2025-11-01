package org.example.smartspring.repository;

import org.example.smartspring.entities.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query("SELECT z FROM Zone z WHERE LOWER(z.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(z.codePostal) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Zone> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Colis c WHERE c.zone.id = :zoneId")
    Long countColisByZone(@Param("zoneId") Long zoneId);

    @Query("SELECT SUM(c.poids) FROM Colis c WHERE c.zone.id = :zoneId")
    Double sumPoidsByZone(@Param("zoneId") Long zoneId);
}
