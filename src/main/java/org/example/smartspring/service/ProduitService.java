package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.entities.Produit;
import org.example.smartspring.mapper.ProduitMapper;
import org.example.smartspring.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    @Transactional
    public ProduitDTO creerProduit(ProduitDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(produit);
        return produitMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public ProduitDTO obtenirProduit(String id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
        return produitMapper.toDTO(produit);
    }

    @Transactional(readOnly = true)
    public Optional<ProduitDTO> obtenirProduitParId(String id) {
        return produitRepository.findById(id)
                .map(produitMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ProduitDTO> obtenirProduitParNom(String nom) {
        return produitRepository.findByNom(nom)
                .map(produitMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> obtenirTousLesProduits() {
        return produitMapper.toDTOList(produitRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ProduitDTO> rechercherProduits(String keyword) {
        return produitMapper.toDTOList(produitRepository.rechercherProduits(keyword));
    }

    @Transactional
    public ProduitDTO modifierProduit(String id, ProduitDTO dto) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setPrixUnitaire(dto.getPrixUnitaire());

        Produit updated = produitRepository.save(produit);
        return produitMapper.toDTO(updated);
    }

    @Transactional
    public void supprimerProduit(String id) {
        if (!produitRepository.existsById(id)) {
            throw new RuntimeException("Produit non trouvé avec l'ID: " + id);
        }
        produitRepository.deleteById(id);
    }
}
