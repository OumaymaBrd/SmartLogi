package org.example.smartspring.controllers;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.reponse.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.services.ColisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    //  Créer un colis
    @PostMapping
    public ResponseEntity<ColisResponseDTO> createColis(@RequestBody ColisRequestDTO colisRequestDTO) {
        ColisResponseDTO created = colisService.createColis(colisRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Récupérer tous les colis
    @GetMapping
    public ResponseEntity<List<ColisResponseDTO>> getAllColis() {
        List<ColisResponseDTO> list = colisService.getAllColis();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Récupérer un colis par id
    @GetMapping("/{id}")
    public ResponseEntity<ColisResponseDTO> getColisById(@PathVariable String id) {
        ColisResponseDTO colis = colisService.getColisById(id);
        if (colis != null) {
            return new ResponseEntity<>(colis, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //  Supprimer un colis par id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColisById(@PathVariable String id) {
        colisService.deleteColisById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
