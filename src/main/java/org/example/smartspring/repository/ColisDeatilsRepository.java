package org.example.smartspring.repository;

import org.example.smartspring.entities.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColisDeatilsRepository extends JpaRepository<Colis,String> {

}
