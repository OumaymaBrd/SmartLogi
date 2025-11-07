package org.example.smartspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<ProduitDTO> creerProduit(@RequestBody ProduitDTO dto) {
        ProduitDTO produit = produitService.creerProduit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> obtenirProduit(@PathVariable String id) {
        return produitService.obtenirProduitParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProduitDTO>> obtenirTousLesProduits() {
        List<ProduitDTO> produits = produitService.obtenirTousLesProduits();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<ProduitDTO> obtenirProduitParNom(@PathVariable String nom) {
        return produitService.obtenirProduitParNom(nom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> modifierProduit(
            @PathVariable String id,
            @RequestBody ProduitDTO dto) {
        ProduitDTO produit = produitService.modifierProduit(id, dto);
        return ResponseEntity.ok(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable String id) {
        produitService.supprimerProduit(id);
        return ResponseEntity.noContent().build();
    }
}
