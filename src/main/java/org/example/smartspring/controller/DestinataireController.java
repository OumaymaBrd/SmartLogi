package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.service.DestinataireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinataires")
@RequiredArgsConstructor
public class DestinataireController {

    private final DestinataireService destinataireService;

    @PostMapping
    public ResponseEntity<DestinataireDTO> creerDestinataire(@RequestBody DestinataireDTO dto) {
        DestinataireDTO destinataire = destinataireService.creerDestinataire(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(destinataire);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinataireDTO> obtenirDestinataire(@PathVariable String id) {
        return destinataireService.obtenirDestinataireParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DestinataireDTO>> obtenirTousLesDestinataires() {
        List<DestinataireDTO> destinataires = destinataireService.obtenirTousLesDestinataires();
        return ResponseEntity.ok(destinataires);
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<DestinataireDTO>> obtenirDestinatairesParVille(@PathVariable String ville) {
        List<DestinataireDTO> destinataires = destinataireService.obtenirDestinatairesParVille(ville);
        return ResponseEntity.ok(destinataires);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinataireDTO> modifierDestinataire(
            @PathVariable String id,
            @RequestBody DestinataireDTO dto) {
        DestinataireDTO destinataire = destinataireService.modifierDestinataire(id, dto);
        return ResponseEntity.ok(destinataire);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerDestinataire(@PathVariable String id) {
        destinataireService.supprimerDestinataire(id);
        return ResponseEntity.noContent().build();
    }
}
