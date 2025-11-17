package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.gestionnairelogistique.AddGestionnaireLogistqueDTO;
import org.example.smartspring.dto.gestionnairelogistique.AddLivreurLivreeDTO;
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

    public String affecterLivreur(
            String numeroColis,
            String idGestionnaire,
            String livreurId,
            String livreurIdLivree
    ) {

        //  Vérifier le gestionnaire
        GestionnaireLogistique gestionnaire = repository.findById(idGestionnaire)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aucun gestionnaire trouvé avec l'id : " + idGestionnaire
                ));

        //  Vérifier le colis
        Colis colis = colisRepository.findByNumeroColis(numeroColis)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aucun colis trouvé avec numéro : " + numeroColis
                ));

        //  Déterminer quel livreur utiliser
        String livreurIdFinal = livreurId != null ? livreurId : livreurIdLivree;
        boolean isLivree = livreurId == null;

        if (livreurIdFinal == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vous devez fournir soit livreur_id soit livreur_id_livree"
            );
        }

        //  Vérifier le livreur
        Livreur livreur = LivreurRepository.findById(livreurIdFinal)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aucun livreur trouvé avec l'id : " + livreurIdFinal
                ));

        //  Vérifier si le livreur est déjà affecté
        if (!isLivree && colis.getLivreur() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le livreur collecteur est déjà affecté à ce colis"
            );
        }

        if (isLivree && colis.getLivreurLivree() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le livreur livré est déjà affecté à ce colis"
            );
        }

        //  Affecter le livreur
        if (isLivree) {
            colis.setLivreurLivree(livreur);
        } else {
            colis.setLivreur(livreur);
        }

        colis.setStatut(StatutColis.TRAITER);
        colisRepository.save(colis);

        // Retourner message selon le type
        return isLivree
                ? "Le colis avec le numéro " + numeroColis + " a été affecté au livreur livré : " + livreurIdFinal
                : "Le colis avec le numéro " + numeroColis + " a été affecté au livreur collecteur : " + livreurIdFinal;
    }








}
