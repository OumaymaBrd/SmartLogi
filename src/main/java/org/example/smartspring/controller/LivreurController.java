package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.UpdateColisDTO;
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

    @GetMapping("/colis/livreur/{id}")
    public List<ConsulterColisAffecterDTO> getColisAffectes(@PathVariable String id) {
        return service.getColisByLivreurId(id);
    }


    @PutMapping("/{colisId}")
    public ResponseEntity<?> updateColis(@PathVariable String colisId,
                                         @RequestBody UpdateColisDTO dto) {

        Colis colis = service.updateColis(colisId, dto);

        String message;
        if (dto.getLivreurId() != null) {
            message = "Le colis " + colisId + " a été affecté au livreur collecteur : " + dto.getLivreurId();
        } else if (dto.getLivreur_id_livree() != null) {
            message = "Le colis " + colisId + " a été affecté au livreur livré : " + dto.getLivreur_id_livree();
        } else if (dto.getStatut() != null) {
            message = "Le colis " + colisId + " a été mis à jour avec le statut : " + colis.getStatut();
        } else {
            message = "Le colis " + colisId + " a été mis à jour avec succès";
        }

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
