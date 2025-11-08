package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.response.ColisCreationResponseDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.service.ColisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    // Création d'un colis pour un nouveau client
    @PostMapping("/nouveau")
    public ResponseEntity<ColisCreationResponseDTO> creerColisPourNouveauClient(@RequestBody ColisDTO dto) {
        Colis colis = colisService.creerColisPourNouveauClient(dto);

        ColisCreationResponseDTO response = ColisCreationResponseDTO.builder()
                .message("Votre colis créé avec succès")
                .numeroSuivi(colis.getNumeroSuivi())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Création d'un colis pour un client existant via ID
    @PostMapping("/existant/{clientId}")
    public ResponseEntity<ColisCreationResponseDTO> creerColisPourClientExistant(
            @PathVariable String clientId,
            @RequestBody ColisDTO dto
    ) {
        Colis colis = colisService.creerColisPourClientExistant(clientId, dto);

        ColisCreationResponseDTO response = ColisCreationResponseDTO.builder()
                .message("Votre colis créé avec succès")
                .numeroSuivi(colis.getNumeroSuivi())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{colisId}")
    public ResponseEntity<Colis> getColisById(@PathVariable String colisId) {
        Colis colis = colisService.getColisById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé avec l'ID: " + colisId));
        return ResponseEntity.ok(colis);
    }


    @GetMapping
    public ResponseEntity<List<Colis>> getAllColis() {
        List<Colis> colisList = colisService.getAllColis();
        return ResponseEntity.ok(colisList);
    }


    @PutMapping("/{colisId}")
    public ResponseEntity<?> updateColis(
            @PathVariable String colisId,
            @RequestBody ColisDTO dto
    ) {
        Colis updatedColis = colisService.updateColis(colisId, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Update Statut Avec Succes!");
    }


    @DeleteMapping("/{colisId}")
    public ResponseEntity<Void> deleteColis(@PathVariable String colisId) {
        colisService.deleteColis(colisId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{colisId}/statut")
    public ResponseEntity<String> updateStatut(@PathVariable String colisId,
                                               @RequestParam StatutColis statut) {

        colisService.updateStatut(colisId, statut);
        return ResponseEntity.ok("Statut du colis mis à jour et historique créé automatiquement");
    }

}
