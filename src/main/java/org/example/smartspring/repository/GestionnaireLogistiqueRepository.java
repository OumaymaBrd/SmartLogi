package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GestionnaireLogistiqueRepository extends JpaRepository<GestionnaireLogistique,String> {



}
