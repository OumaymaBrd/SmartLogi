package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.AssignerLivreurDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.colis.ColisFilterDTO;
import org.example.smartspring.dto.colis.HistoriqueColisDTO;
import org.example.smartspring.dto.colis.StatistiquesDTO;
import org.example.smartspring.dto.response.ColisCreationResponseDTO;
import org.example.smartspring.service.ColisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    @PostMapping("/nouveau")
    public ResponseEntity<ColisDTO> creerColisPourNouveauClient(@RequestBody ColisDTO dto) {
        ColisDTO colis = colisService.creerColisPourNouveauClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(colis);
    }

    @PostMapping("/existant/{clientId}")
    public ResponseEntity<ColisDTO> creerColisPourClientExistant(
            @PathVariable String clientId,
            @RequestBody ColisDTO dto
    ) {
        ColisDTO colis = colisService.creerColisPourClientExistant(clientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(colis);
    }

    @GetMapping("/{colisId}")
    public ResponseEntity<ColisDTO> getColisById(@PathVariable String colisId) {
        return colisService.getColisDTOById(colisId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ColisDTO>> getAllColis() {
        List<ColisDTO> colisList = colisService.getAllColisDTO();
        return ResponseEntity.ok(colisList);
    }

    @PutMapping("/{colisId}")
    public ResponseEntity<ColisDTO> updateColis(
            @PathVariable String colisId,
            @RequestBody ColisDTO dto
    ) {
        ColisDTO updatedColis = colisService.updateColis(colisId, dto);
        return ResponseEntity.ok(updatedColis);
    }

    @DeleteMapping("/{colisId}")
    public ResponseEntity<Void> deleteColis(@PathVariable String colisId) {
        colisService.deleteColis(colisId);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS GESTIONNAIRE LOGISTIQUE ==========

    @GetMapping("/gestionnaire/{gestionnaireId}")
    public ResponseEntity<Page<ColisDTO>> getTousLesColis(
            @PathVariable String gestionnaireId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ColisDTO> colis = colisService.getTousLesColisDTO(pageable);
        return ResponseEntity.ok(colis);
    }

    @PostMapping("/gestionnaire/{gestionnaireId}/filtrer")
    public ResponseEntity<Page<ColisDTO>> filtrerColis(
            @PathVariable String gestionnaireId,
            @RequestBody ColisFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ColisDTO> colis = colisService.filtrerColisDTO(filterDTO, pageable);
        return ResponseEntity.ok(colis);
    }

    @PutMapping("/gestionnaire/{gestionnaireId}/colis/{colisId}/assigner")
    public ResponseEntity<ColisDTO> assignerColisAuLivreur(
            @PathVariable String gestionnaireId,
            @PathVariable String colisId,
            @RequestBody AssignerLivreurDTO dto
    ) {
        ColisDTO colis = colisService.assignerColisAuLivreurDTO(colisId, dto.getLivreurId(), gestionnaireId);
        return ResponseEntity.ok(colis);
    }

    @PutMapping("/gestionnaire/{gestionnaireId}/colis/{colisId}")
    public ResponseEntity<ColisDTO> modifierColis(
            @PathVariable String gestionnaireId,
            @PathVariable String colisId,
            @RequestBody ColisDTO dto
    ) {
        ColisDTO colis = colisService.modifierColisParGestionnaireDTO(colisId, dto, gestionnaireId);
        return ResponseEntity.ok(colis);
    }

    @DeleteMapping("/gestionnaire/{gestionnaireId}/colis/{colisId}")
    public ResponseEntity<Void> supprimerColis(
            @PathVariable String gestionnaireId,
            @PathVariable String colisId
    ) {
        colisService.supprimerColisParGestionnaire(colisId, gestionnaireId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gestionnaire/{gestionnaireId}/statistiques")
    public ResponseEntity<StatistiquesDTO> obtenirStatistiques(
            @PathVariable String gestionnaireId
    ) {
        StatistiquesDTO stats = colisService.obtenirStatistiques();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/gestionnaire/{gestionnaireId}/colis/{colisId}/historique")
    public ResponseEntity<List<HistoriqueColisDTO>> obtenirHistoriqueColis(
            @PathVariable String gestionnaireId,
            @PathVariable String colisId
    ) {
        List<HistoriqueColisDTO> historique = colisService.obtenirHistoriqueColis(colisId);
        return ResponseEntity.ok(historique);
    }
}
