package org.example.smartspring.repository;

import org.example.smartspring.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, String> {

    Optional<Produit> findByNom(String nom);
}
