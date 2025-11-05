package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.AddColisDTO;
import org.example.smartspring.dto.colis.UpdateColisDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.service.ColisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    @PostMapping
    public ResponseEntity<ColisDTO> createColis(@Valid @RequestBody AddColisDTO dto) {
        return new ResponseEntity<>(colisService.createColis(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllColis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String priorite,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ColisDTO> colis = colisService.getAllColis(pageable, statut, zone, ville, priorite, dateDebut, dateFin);

        if (colis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun colis trouvé pour ces critères.");
        }

        return ResponseEntity.ok(colis);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ColisDTO> getColisById(@PathVariable String id) {
        return ResponseEntity.ok(colisService.getColisById(id));
    }

    @GetMapping("/suivi/{numeroSuivi}")
    public ResponseEntity<ColisDTO> getColisByNumeroSuivi(@PathVariable String numeroSuivi) {
        return ResponseEntity.ok(colisService.getColisByNumeroSuivi(numeroSuivi));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColisDTO> updateColis(@PathVariable String id, @Valid @RequestBody UpdateColisDTO dto) {
        return ResponseEntity.ok(colisService.updateColis(id, dto));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<ColisDTO> updateStatut(@PathVariable String id, @RequestBody Map<String, String> body) {
        StatutColis statut = StatutColis.valueOf(body.get("statut"));
        return ResponseEntity.ok(colisService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable String id) {
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ColisDTO>> searchColis(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(colisService.searchColis(keyword, pageable));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ColisDTO>> getColisByStatut(@PathVariable StatutColis statut) {
        return ResponseEntity.ok(colisService.getColisByStatut(statut));
    }

    @GetMapping("/priorite/{priorite}")
    public ResponseEntity<List<ColisDTO>> getColisByPriorite(@PathVariable PrioriteColis priorite) {
        return ResponseEntity.ok(colisService.getColisByPriorite(priorite));
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<ColisDTO>> getColisByLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(colisService.getColisByLivreur(livreurId));
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<ColisDTO>> getColisByZone(@PathVariable String zoneId) {
        return ResponseEntity.ok(colisService.getColisByZone(zoneId));
    }
}
