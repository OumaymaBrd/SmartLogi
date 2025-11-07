package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.gestionnaire.GestionnaireLogistiqueDTO;
import org.example.smartspring.dto.gestionnaire.GestionnaireResponseDTO;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.GestionnaireLogistiqueMapper;
import org.example.smartspring.repository.GestionnaireLogistiqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GestionnaireLogistiqueService {

    private final GestionnaireLogistiqueRepository gestionnaireRepository;
    private final GestionnaireLogistiqueMapper mapper;

    @Transactional
    public GestionnaireResponseDTO creerGestionnaire(GestionnaireLogistiqueDTO dto) {
        // Vérifier si l'email existe déjà
        if (gestionnaireRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Un gestionnaire avec cet email existe déjà");
        }

        GestionnaireLogistique gestionnaire = mapper.toEntity(dto);
        GestionnaireLogistique saved = gestionnaireRepository.save(gestionnaire);

        return mapper.toResponseDTO(saved);
    }

    public Optional<GestionnaireResponseDTO> obtenirGestionnaireParId(String id) {
        return gestionnaireRepository.findById(id)
                .map(mapper::toResponseDTO);
    }

    public GestionnaireLogistique obtenirGestionnaireEntityParId(String id) {
        return gestionnaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gestionnaire non trouvé avec l'ID: " + id));
    }
}
