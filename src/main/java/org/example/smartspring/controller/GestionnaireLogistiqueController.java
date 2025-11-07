package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.gestionnaire.GestionnaireLogistiqueDTO;
import org.example.smartspring.dto.gestionnaire.GestionnaireResponseDTO;
import org.example.smartspring.service.GestionnaireLogistiqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/gestionnaires")
@RequiredArgsConstructor
public class GestionnaireLogistiqueController {

    private final GestionnaireLogistiqueService gestionnaireService;

    @PostMapping
    public ResponseEntity<GestionnaireResponseDTO> creerGestionnaire(@RequestBody GestionnaireLogistiqueDTO dto) {
        GestionnaireResponseDTO gestionnaire = gestionnaireService.creerGestionnaire(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gestionnaire);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestionnaireResponseDTO> obtenirGestionnaire(@PathVariable String id) {
        return gestionnaireService.obtenirGestionnaireParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
