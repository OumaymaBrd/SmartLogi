package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.Colis.AddColisDTO;
import org.example.smartspring.dto.Colis.ColisDTO;
import org.example.smartspring.dto.Colis.UpdateColisDTO;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.service.ColisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService service;

    @PostMapping
    public ResponseEntity<ColisDTO> create(@Valid @RequestBody AddColisDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColisDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ColisDTO>> getAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<Page<ColisDTO>> getByStatut(
            @PathVariable StatutColis statut,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getByStatut(statut, pageable));
    }

    @GetMapping("/priorite/{priorite}")
    public ResponseEntity<Page<ColisDTO>> getByPriorite(
            @PathVariable PrioriteColis priorite,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getByPriorite(priorite, pageable));
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<Page<ColisDTO>> getByVille(
            @PathVariable String ville,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getByVille(ville, pageable));
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<Page<ColisDTO>> getByZone(
            @PathVariable Long zoneId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getByZone(zoneId, pageable));
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<ColisDTO>> getByLivreur(@PathVariable Long livreurId) {
        return ResponseEntity.ok(service.getByLivreur(livreurId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ColisDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.getByClientExpediteur(clientId));
    }

    @GetMapping("/destinataire/{destinataireId}")
    public ResponseEntity<List<ColisDTO>> getByDestinataire(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(service.getByDestinataire(destinataireId));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ColisDTO>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.search(keyword, pageable));
    }

    @GetMapping("/livreur/{livreurId}/stats")
    public ResponseEntity<Map<String, Object>> getLivreurStats(@PathVariable Long livreurId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreColis", service.countByLivreur(livreurId));
        stats.put("poidsTotal", service.sumPoidsByLivreur(livreurId));
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/zone/{zoneId}/stats")
    public ResponseEntity<Map<String, Object>> getZoneStats(@PathVariable Long zoneId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreColis", service.countByZone(zoneId));
        stats.put("poidsTotal", service.sumPoidsByZone(zoneId));
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColisDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateColisDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
