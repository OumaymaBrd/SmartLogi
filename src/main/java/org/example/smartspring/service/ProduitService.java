package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.produit.AddProduitDTO;
import org.example.smartspring.dto.produit.ProduitDTO;
import org.example.smartspring.dto.produit.UpdateProduitDTO;
import org.example.smartspring.entities.Produit;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ProduitMapper;
import org.example.smartspring.repository.ProduitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProduitService {

    private final ProduitRepository repository;
    private final ProduitMapper mapper;

    public ProduitDTO createProduit(AddProduitDTO dto) {
        log.debug("Creating new produit: {}", dto.getNom());
        Produit entity = mapper.toEntity(dto);
        Produit saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<ProduitDTO> getAllProduits(Pageable pageable) {
        log.debug("Fetching all produits with pagination");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProduitDTO getProduitById(Long id) {
        log.debug("Fetching produit by id: {}", id);
        Produit entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit not found with id: " + id));
        return mapper.toDto(entity);
    }

    public ProduitDTO updateProduit(Long id, UpdateProduitDTO dto) {
        log.debug("Updating produit with id: {}", id);
        Produit entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit not found with id: " + id));

        mapper.updateEntityFromDto(dto, entity);
        Produit updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    public void deleteProduit(Long id) {
        log.debug("Deleting produit with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produit not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ProduitDTO> searchProduits(String keyword, Pageable pageable) {
        log.debug("Searching produits with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }
}
