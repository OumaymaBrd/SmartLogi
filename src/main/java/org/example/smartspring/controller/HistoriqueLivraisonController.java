package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.historique.HistoriqueLivraisonDTO;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.service.HistoriqueLivraisonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/historiques")
@RequiredArgsConstructor
public class HistoriqueLivraisonController {

    private final HistoriqueLivraisonService historiqueLivraisonService;

    @GetMapping("/colis/{colisId}")
    public ResponseEntity<List<HistoriqueLivraisonDTO>> getHistoriqueByColisId(@PathVariable String colisId) {
        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByColisId(colisId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<HistoriqueLivraisonDTO>> getHistoriqueByStatut(@PathVariable StatutColis statut) {
        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByStatut(statut));
    }

    @GetMapping("/periode")
    public ResponseEntity<List<HistoriqueLivraisonDTO>> getHistoriqueByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByPeriode(
                dateDebut.atStartOfDay(),
                dateFin.atTime(23, 59, 59)
        ));
    }
}
