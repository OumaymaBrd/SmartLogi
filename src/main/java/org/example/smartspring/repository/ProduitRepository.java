package org.example.smartspring.repository;

import org.example.smartspring.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByCategorie(String categorie);

    @Query("SELECT p FROM Produit p WHERE " +
            "LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.categorie) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Produit> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
