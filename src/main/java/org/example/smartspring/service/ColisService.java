package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.Colis.AddColisDTO;
import org.example.smartspring.dto.Colis.ColisDTO;
import org.example.smartspring.dto.Colis.UpdateColisDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColisService {

    private final ColisRepository repository;
    private final ClientExpediteurRepository clientExpediteurRepository;
    private final DestinataireRepository destinataireRepository;
    private final LivreurRepository livreurRepository;
    private final ZoneRepository zoneRepository;
    private final HistoriqueLivraisonRepository historiqueRepository;
    private final ColisMapper mapper;

    @Transactional
    public ColisDTO create(AddColisDTO dto) {
        log.info("Creating new colis for client: {}", dto.getClientExpediteurId());

        // Validate references
        if (!clientExpediteurRepository.existsById(dto.getClientExpediteurId())) {
            throw new RuntimeException("Client expéditeur non trouvé");
        }
        if (!destinataireRepository.existsById(dto.getDestinataireId())) {
            throw new RuntimeException("Destinataire non trouvé");
        }
        if (dto.getLivreurId() != null && !livreurRepository.existsById(dto.getLivreurId())) {
            throw new RuntimeException("Livreur non trouvé");
        }
        if (dto.getZoneId() != null && !zoneRepository.existsById(dto.getZoneId())) {
            throw new RuntimeException("Zone non trouvée");
        }

        Colis entity = mapper.toEntity(dto);
        Colis saved = repository.save(entity);

        // Create initial history entry
        HistoriqueLivraison historique = HistoriqueLivraison.builder()
                .colis(saved)
                .statut(saved.getStatut())
                .commentaire("Colis créé")
                .build();
        historiqueRepository.save(historique);

        log.info("Colis created with ID: {}", saved.getId());
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ColisDTO getById(Long id) {
        log.debug("Fetching colis with ID: {}", id);
        Colis entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getAll(Pageable pageable) {
        log.debug("Fetching all colis with pagination");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getByStatut(StatutColis statut, Pageable pageable) {
        log.debug("Fetching colis by statut: {}", statut);
        return repository.findByStatut(statut, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getByPriorite(PrioriteColis priorite, Pageable pageable) {
        log.debug("Fetching colis by priorite: {}", priorite);
        return repository.findByPriorite(priorite, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getByVille(String ville, Pageable pageable) {
        log.debug("Fetching colis by ville: {}", ville);
        return repository.findByVilleDestination(ville, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> getByZone(Long zoneId, Pageable pageable) {
        log.debug("Fetching colis by zone: {}", zoneId);
        return repository.findByZoneId(zoneId, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getByLivreur(Long livreurId) {
        log.debug("Fetching colis by livreur: {}", livreurId);
        return repository.findByLivreurId(livreurId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getByClientExpediteur(Long clientId) {
        log.debug("Fetching colis by client expediteur: {}", clientId);
        return repository.findByClientExpediteurId(clientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ColisDTO> getByDestinataire(Long destinataireId) {
        log.debug("Fetching colis by destinataire: {}", destinataireId);
        return repository.findByDestinataireId(destinataireId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ColisDTO> search(String keyword, Pageable pageable) {
        log.debug("Searching colis with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }

    @Transactional
    public ColisDTO update(Long id, UpdateColisDTO dto) {
        log.info("Updating colis with ID: {}", id);

        Colis entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé avec l'ID: " + id));

        StatutColis oldStatut = entity.getStatut();

        if (dto.getLivreurId() != null && !livreurRepository.existsById(dto.getLivreurId())) {
            throw new RuntimeException("Livreur non trouvé");
        }
        if (dto.getZoneId() != null && !zoneRepository.existsById(dto.getZoneId())) {
            throw new RuntimeException("Zone non trouvée");
        }

        mapper.updateEntityFromDto(dto, entity);
        Colis updated = repository.save(entity);

        // Create history entry if status changed
        if (dto.getStatut() != null && !dto.getStatut().equals(oldStatut)) {
            HistoriqueLivraison historique = HistoriqueLivraison.builder()
                    .colis(updated)
                    .statut(updated.getStatut())
                    .commentaire("Statut changé de " + oldStatut + " à " + updated.getStatut())
                    .build();
            historiqueRepository.save(historique);
        }

        log.info("Colis updated with ID: {}", updated.getId());
        return mapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting colis with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Colis non trouvé avec l'ID: " + id);
        }

        repository.deleteById(id);
        log.info("Colis deleted with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public Long countByLivreur(Long livreurId) {
        return repository.countByLivreurId(livreurId);
    }

    @Transactional(readOnly = true)
    public BigDecimal sumPoidsByLivreur(Long livreurId) {
        BigDecimal total = repository.sumPoidsByLivreurId(livreurId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public Long countByZone(Long zoneId) {
        return repository.countByZoneId(zoneId);
    }

    @Transactional(readOnly = true)
    public BigDecimal sumPoidsByZone(Long zoneId) {
        BigDecimal total = repository.sumPoidsByZoneId(zoneId);
        return total != null ? total : BigDecimal.ZERO;
    }
}
