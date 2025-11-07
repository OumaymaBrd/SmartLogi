package org.example.smartspring.repository;

import org.example.smartspring.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, String> {

    Optional<Produit> findByNom(String nom);

    @Query("SELECT p FROM Produit p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Produit> rechercherProduits(String keyword);
}
