package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historiques")
@RequiredArgsConstructor
public class HistoriqueLivraisonController {

//    private final HistoriqueLivraisonService historiqueLivraisonService;
//
//    @GetMapping("/colis/{colisId}")
//    public ResponseEntity<List<HistoriqueColisDTO>> getHistoriqueByColisId(@PathVariable String colisId) {
//        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByColisId(colisId));
//    }
//
//    @GetMapping("/statut/{statut}")
//    public ResponseEntity<List<HistoriqueColisDTO>> getHistoriqueByStatut(@PathVariable StatutColis statut) {
//        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByStatut(statut));
//    }
//
//    @GetMapping("/periode")
//    public ResponseEntity<List<HistoriqueColisDTO>> getHistoriqueByPeriode(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
//        return ResponseEntity.ok(historiqueLivraisonService.getHistoriqueByPeriode(
//                dateDebut.atStartOfDay(),
//                dateFin.atTime(23, 59, 59)
//        ));
//    }
}
