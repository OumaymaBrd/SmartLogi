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

//    private final LivreurService livreurService;
//
//    @PostMapping
//    public ResponseEntity<LivreurDTO> createLivreur(@Valid @RequestBody AddLivreurDTO dto) {
//        return new ResponseEntity<>(livreurService.createLivreur(dto), HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getAllLivreurs() {
//        List<LivreurDTO> liste=livreurService.getAllLivreurs();
//        if (liste.isEmpty()){
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("Liste Livreur est Vide !!");
//        }
//        return ResponseEntity.ok(liste);
//
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<LivreurDTO> getLivreurById(@PathVariable String id) {
//        return ResponseEntity.ok(livreurService.getLivreurById(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LivreurDTO> updateLivreur(@PathVariable String id, @Valid @RequestBody UpdateLivreurDTO dto) {
//        return ResponseEntity.ok(livreurService.updateLivreur(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteLivreur(@PathVariable String id) {
//        livreurService.deleteLivreur(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<Page<LivreurDTO>> searchLivreurs(@RequestParam String keyword, Pageable pageable) {
//        return ResponseEntity.ok(livreurService.searchLivreurs(keyword, pageable));
//    }
//
//    @GetMapping("/zone/{zoneId}")
//    public ResponseEntity<List<LivreurDTO>> getLivreursByZone(@PathVariable String zoneId) {
//        return ResponseEntity.ok(livreurService.getLivreursByZone(zoneId));
//    }
//
//    @GetMapping("/{id}/stats")
//    public ResponseEntity<Map<String, Object>> getLivreurStats(@PathVariable String id) {
//        return ResponseEntity.ok(livreurService.getLivreurStats(id));
//    }
}
