package org.example.smartspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.produit.AddProduitDTO;
import org.example.smartspring.dto.produit.UpdateProduitDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody AddProduitDTO dto) {
        return new ResponseEntity<>(produitService.createProduit(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProduitDTO>> getAllProduits(Pageable pageable) {
        return ResponseEntity.ok(produitService.getAllProduits(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable Long id, @Valid @RequestBody UpdateProduitDTO dto) {
        return ResponseEntity.ok(produitService.updateProduit(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProduitDTO>> searchProduits(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(produitService.searchProduits(keyword, pageable));
    }
}
