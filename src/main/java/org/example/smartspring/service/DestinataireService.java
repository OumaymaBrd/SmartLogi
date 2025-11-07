package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.entities.Destinataire;
import org.example.smartspring.mapper.DestinataireMapper;
import org.example.smartspring.repository.DestinataireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinataireService {

    private final DestinataireRepository destinataireRepository;
    private final DestinataireMapper destinataireMapper;

    @Transactional
    public DestinataireDTO creerDestinataire(DestinataireDTO dto) {
        Destinataire destinataire = destinataireMapper.toEntity(dto);
        Destinataire saved = destinataireRepository.save(destinataire);
        return destinataireMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public DestinataireDTO obtenirDestinataire(String id) {
        Destinataire destinataire = destinataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé avec l'ID: " + id));
        return destinataireMapper.toDTO(destinataire);
    }

    @Transactional(readOnly = true)
    public Optional<DestinataireDTO> obtenirDestinataireParId(String id) {
        return destinataireRepository.findById(id)
                .map(destinataireMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<DestinataireDTO> obtenirTousLesDestinataires() {
        return destinataireMapper.toDTOList(destinataireRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<DestinataireDTO> rechercherDestinataires(String keyword) {
        return destinataireMapper.toDTOList(destinataireRepository.rechercherDestinataires(keyword));
    }

    @Transactional(readOnly = true)
    public List<DestinataireDTO> obtenirDestinatairesParVille(String ville) {
        return destinataireMapper.toDTOList(destinataireRepository.findByVille(ville));
    }

    @Transactional
    public DestinataireDTO modifierDestinataire(String id, DestinataireDTO dto) {
        Destinataire destinataire = destinataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé avec l'ID: " + id));

        destinataire.setNom(dto.getNom());
        destinataire.setPrenom(dto.getPrenom());
        destinataire.setEmail(dto.getEmail());
        destinataire.setTelephone(dto.getTelephone());
        destinataire.setAdresse(dto.getAdresse());
        destinataire.setVille(dto.getVille());

        Destinataire updated = destinataireRepository.save(destinataire);
        return destinataireMapper.toDTO(updated);
    }

    @Transactional
    public void supprimerDestinataire(String id) {
        if (!destinataireRepository.existsById(id)) {
            throw new RuntimeException("Destinataire non trouvé avec l'ID: " + id);
        }
        destinataireRepository.deleteById(id);
    }
}
