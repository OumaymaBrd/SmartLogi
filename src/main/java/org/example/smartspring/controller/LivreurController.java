package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.service.LivreurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livreurs")
@RequiredArgsConstructor
public class LivreurController {

    private final LivreurService livreurService;

    @PostMapping
    public ResponseEntity<LivreurDTO> creerLivreur(@RequestBody LivreurDTO dto) {
        LivreurDTO livreur = livreurService.creerLivreur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livreur);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreurDTO> obtenirLivreur(@PathVariable String id) {
        return livreurService.obtenirLivreurParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LivreurDTO>> obtenirTousLesLivreurs() {
        List<LivreurDTO> livreurs = livreurService.obtenirTousLesLivreurs();
        return ResponseEntity.ok(livreurs);
    }

    @GetMapping("/zone/{zoneNom}")
    public ResponseEntity<List<LivreurDTO>> obtenirLivreursParZone(@PathVariable String zoneNom) {
        List<LivreurDTO> livreurs = livreurService.obtenirLivreursParZone(zoneNom);
        return ResponseEntity.ok(livreurs);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<LivreurDTO>> obtenirLivreursDisponibles() {
        List<LivreurDTO> livreurs = livreurService.obtenirLivreursDisponibles();
        return ResponseEntity.ok(livreurs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreurDTO> modifierLivreur(
            @PathVariable String id,
            @RequestBody LivreurDTO dto) {
        LivreurDTO livreur = livreurService.modifierLivreur(id, dto);
        return ResponseEntity.ok(livreur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivreur(@PathVariable String id) {
        livreurService.supprimerLivreur(id);
        return ResponseEntity.noContent().build();
    }
}
