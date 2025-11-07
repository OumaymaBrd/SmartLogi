package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.GestionnaireLogistiqueDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.GestionnaireLogistique;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.mapper.GestionnaireLogistiqueMapper;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.repository.GestionnaireLogistiqueRepository;
import org.example.smartspring.repository.LivreurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service

@RequiredArgsConstructor
public class GestionnaireLogistiqueService {

    private final GestionnaireLogistiqueRepository repository;
    private final GestionnaireLogistiqueMapper mapper;
    private final ColisRepository colisRepository;
    private final LivreurRepository LivreurRepository;

    public GestionnaireLogistiqueDTO create(AddGestionnaireLogistqueDTO dto) {

        GestionnaireLogistique entity = mapper.toEntity(dto);
        GestionnaireLogistique saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public GestionnaireLogistiqueDTO affecterLivreur(String numeroColis,
                                                     String idGestionnaire,
                                                     String idLivreur) {

        GestionnaireLogistique gestionnaire = repository.findById(idGestionnaire)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aucun gestionnaire trouvé avec l'id : " + idGestionnaire
                ));

        Colis colis = colisRepository.findByNumeroColis(numeroColis)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aucun colis trouvé avec Numero Colis : " + numeroColis
                ));

        Livreur livreur = LivreurRepository.findById(idLivreur)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aucun livreur trouvé avec l'id : " + idLivreur
                ));

        String livreurExistantId = colisRepository.findLivreurIdByNumeroColis(numeroColis);
        if (livreurExistantId == null) {
            colis.setLivreur(livreur);
            colis.setStatut(StatutColis.TRAITER);
            colisRepository.save(colis);
            return mapper.toDto(gestionnaire);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Le livreur est déjà affecté à ce colis"
            );
        }
    }





}
