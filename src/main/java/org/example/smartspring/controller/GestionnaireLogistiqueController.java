package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.clientexpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.clientexpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.service.GestionnaireLogistiqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
@Slf4j
@RequestMapping("/gestionnairelogistique")
public class GestionnaireLogistiqueController {

    private final GestionnaireLogistiqueService service;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody AddGestionnaireLogistqueDTO dto) {

        GestionnaireLogistiqueDTO created = service.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(" Creation Gestionnaire Logistique Avec Succes");
    }

    @PutMapping("/affecter-livreur")
    public ResponseEntity<?> affecterLivreur(
            @RequestParam String numero_colis,
            @RequestParam String idGestionnaire,
            @RequestParam(required = false, name = "livreur_id") String livreurId,           // correspond à livreur_id en BDD
            @RequestParam(required = false, name = "livreur_id_livree") String livreurIdLivree // correspond à livreur_id_livree en BDD
    ) {

        if (livreurId == null && livreurIdLivree == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Vous devez fournir soit livreur_id soit livreur_id_livree");
        }

        String message = service.affecterLivreur(numero_colis, idGestionnaire, livreurId, livreurIdLivree);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }



}
