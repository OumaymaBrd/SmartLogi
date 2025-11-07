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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
