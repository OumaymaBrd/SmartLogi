package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.livreur.*;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.service.ColisService;
import org.example.smartspring.service.LivreurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/livreurs")
@RequiredArgsConstructor
public class LivreurController {

    private final ColisService service;

    @GetMapping("/{id}")
    public List<ConsulterColisAffecterDTO> getColisAffectes(@PathVariable String id) {
        return service.getColisByLivreurId(id);
    }

    @PutMapping("/colis/{colisId}")
    public ResponseEntity<?> updateStatut(@PathVariable String colisId,
                              @RequestBody UpdateColisStatutDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Colis Avec Succes avec Statut Collecte ");
    }


}
