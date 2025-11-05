package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.colis.AddColisDTO;
import org.example.smartspring.dto.colis.ColisDTO;
import org.example.smartspring.dto.colis.UpdateColisDTO;
import org.example.smartspring.entities.*;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ColisService {

    private final ColisRepository repository;
    private final ClientExpediteurRepository clientExpediteurRepository;
    private final DestinataireRepository destinataireRepository;
    private final LivreurRepository livreurRepository;
    private final ZoneRepository zoneRepository;
    private final HistoriqueLivraisonRepository historiqueLivraisonRepository;
    private final ColisMapper mapper;

    public ColisDTO createColis(AddColisDTO dto) {
        log.debug("Creating new colis: {}", dto.getNumeroSuivi());

        ClientExpediteur clientExpediteur = clientExpediteurRepository.findById(dto.getClientExpediteurId())
                .orElseThrow(() -> new ResourceNotFoundException("ClientExpediteur not found with id: " + dto.getClientExpediteurId()));

        Destinataire destinataire = destinataireRepository.findById(dto.getDestinataireId())
                .orElseThrow(() -> new ResourceNotFoundException("Destinataire not found with id: " + dto.getDestinataireId()));

        Livreur livreur = livreurRepository.findById(dto.getLivreurId())
                .orElseThrow(() -> new ResourceNotFoundException("Livreur not found with id: " + dto.getLivreurId()));

        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + dto.getZoneId()));

        Colis entity = mapper.toEntity(dto);
        entity.setClientExpediteur(clientExpediteur);
        entity.setDestinataire(destinataire);
        entity.setLivreur(livreur);
        entity.setZone(zone);

        Colis saved = repository.save(entity);

        // Create initial history entry
        createHistoryEntry(saved, saved.getStatut(), "Colis créé");

        return mapper.toDto(saved);
    }


    @Transactional(readOnly = true)
    public Page<ColisDTO> getAllColis(
            Pageable pageable,
            String statut,
            String zone,
            String ville,
            String priorite,
            LocalDateTime dateDebut,
            LocalDateTime dateFin
    ) {
        Specification<Colis> spec = Specification.where(null);

        if (statut != null && !statut.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("statut"), StatutColis.valueOf(statut)));
        }

        if (priorite != null && !priorite.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("priorite"), PrioriteColis.valueOf(priorite)));
        }

        if (zone != null && !zone.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("zone").get("nom"), zone));
        }

        if (ville != null && !ville.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("villeDestination"), ville));
        }

        if (dateDebut != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("dateLivraisonReelle"), dateDebut));
        }

        if (dateFin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("dateLivraisonReelle"), dateFin));
        }

        return repository.findAll(spec, pageable)
                .map(mapper::toDto);
    }


    @Transactional(readOnly = true)
    public ColisDTO getColisById(String id) {
        log.debug("Fetching colis by id: {}", id);
        Colis entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public ColisDTO getColisByNumeroSuivi(String numeroSuivi) {
        log.debug("Fetching colis by numero suivi: {}", numeroSuivi);
        Colis entity = repository.findByNumeroSuivi(numeroSuivi)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found with numero suivi: " + numeroSuivi));
        return mapper.toDto(entity);
    }

    public ColisDTO updateColis(String id, UpdateColisDTO dto) {
        log.debug("Updating colis with id: {}", id);
        Colis entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found with id: " + id));

        StatutColis oldStatut = entity.getStatut();

        if (dto.getClientExpediteurId() != null) {
            ClientExpediteur clientExpediteur = clientExpediteurRepository.findById(dto.getClientExpediteurId())
                    .orElseThrow(() -> new ResourceNotFoundException("ClientExpediteur not found"));
            entity.setClientExpediteur(clientExpediteur);
        }

        if (dto.getDestinataireId() != null) {
            Destinataire destinataire = destinataireRepository.findById(dto.getDestinataireId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destinataire not found"));
            entity.setDestinataire(destinataire);
        }

        if (dto.getLivreurId() != null) {
            Livreur livreur = livreurRepository.findById(dto.getLivreurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Livreur not found"));
            entity.setLivreur(livreur);
        }

        if (dto.getZoneId() != null) {
            Zone zone = zoneRepository.findById(dto.getZoneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
            entity.setZone(zone);
        }

        mapper.updateEntityFromDto(dto, entity);

        // If status changed, create history entry
        if (dto.getStatut() != null && !dto.getStatut().equals(oldStatut)) {
            createHistoryEntry(entity, dto.getStatut(), "Statut mis à jour");
        }

        Colis updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    public ColisDTO updateColisStatut(String id, StatutColis newStatut) {
        log.debug("Updating colis status with id: {} to {}", id, newStatut);
        Colis entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found with id: " + id));

        StatutColis oldStatut = entity.getStatut();
        entity.setStatut(newStatut);

        if (newStatut == StatutColis.LIVRE) {
            entity.setDateLivraisonReelle(LocalDateTime.now());
        }

        Colis updated = repository.save(entity);

        // Create history entry
        createHistoryEntry(updated, newStatut, "Statut changé de " + oldStatut + " à " + newStatut);

        return mapper.toDto(updated);
    }

    public ColisDTO updateStatut(String id, StatutColis newStatut){

        return updateColisStatut(id, newStatut);

    }

    public void deleteColis(String id) {
        log.debug("Deleting colis with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Colis not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> searchColis(String keyword, Pageable pageable) {
        log.debug("Searching colis with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getColisByStatut(StatutColis statut) {
        log.debug("Fetching colis by statut: {}", statut);
        return repository.findByStatut(statut).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getColisByLivreur(String livreurId) {
        log.debug("Fetching colis by livreur: {}", livreurId);
        return repository.findByLivreurId(livreurId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getColisByZone(String zoneId) {
        log.debug("Fetching colis by zone: {}", zoneId);
        return repository.findByZoneId(zoneId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getColisByPriorite(PrioriteColis priorite) {
        log.debug("Fetching colis by priorite: {}", priorite);
        return repository.findByPriorite(priorite).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private void createHistoryEntry(Colis colis, StatutColis statut, String commentaire) {
        HistoriqueLivraison historique = new HistoriqueLivraison();
        historique.setColis(colis);
        historique.setStatut(statut);
        historique.setDateChangement(LocalDateTime.now());
        historique.setCommentaire(commentaire);
        historiqueLivraisonRepository.save(historique);
    }
}
