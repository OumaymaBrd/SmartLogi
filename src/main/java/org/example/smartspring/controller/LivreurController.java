package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.livreur.AddLivreurDTO;
import org.example.smartspring.dto.livreur.UpdateLivreurDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
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

    private final LivreurService livreurService;

    @PostMapping
    public ResponseEntity<LivreurDTO> createLivreur(@Valid @RequestBody AddLivreurDTO dto) {
        return new ResponseEntity<>(livreurService.createLivreur(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<LivreurDTO>> getAllLivreurs(Pageable pageable) {
        return ResponseEntity.ok(livreurService.getAllLivreurs(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreurDTO> getLivreurById(@PathVariable Long id) {
        return ResponseEntity.ok(livreurService.getLivreurById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreurDTO> updateLivreur(@PathVariable Long id, @Valid @RequestBody UpdateLivreurDTO dto) {
        return ResponseEntity.ok(livreurService.updateLivreur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        livreurService.deleteLivreur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LivreurDTO>> searchLivreurs(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(livreurService.searchLivreurs(keyword, pageable));
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<LivreurDTO>> getLivreursByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(livreurService.getLivreursByZone(zoneId));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getLivreurStats(@PathVariable Long id) {
        return ResponseEntity.ok(livreurService.getLivreurStats(id));
    }
}
