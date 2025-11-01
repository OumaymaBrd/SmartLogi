package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.livreur.AddLivreurDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.livreur.UpdateLivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.entities.Zone;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.LivreurMapper;
import org.example.smartspring.repository.LivreurRepository;
import org.example.smartspring.repository.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LivreurService {

    private final LivreurRepository repository;
    private final ZoneRepository zoneRepository;
    private final LivreurMapper mapper;

    public LivreurDTO createLivreur(AddLivreurDTO dto) {
        log.debug("Creating new livreur: {} {}", dto.getNom(), dto.getPrenom());

        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + dto.getZoneId()));

        Livreur entity = mapper.toEntity(dto);
        entity.setZone(zone);

        Livreur saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<LivreurDTO> getAllLivreurs(Pageable pageable) {
        log.debug("Fetching all livreurs with pagination");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public LivreurDTO getLivreurById(Long id) {
        log.debug("Fetching livreur by id: {}", id);
        Livreur entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur not found with id: " + id));
        return mapper.toDto(entity);
    }

    public LivreurDTO updateLivreur(Long id, UpdateLivreurDTO dto) {
        log.debug("Updating livreur with id: {}", id);
        Livreur entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur not found with id: " + id));

        if (dto.getZoneId() != null) {
            Zone zone = zoneRepository.findById(dto.getZoneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + dto.getZoneId()));
            entity.setZone(zone);
        }

        mapper.updateEntityFromDto(dto, entity);
        Livreur updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    public void deleteLivreur(Long id) {
        log.debug("Deleting livreur with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Livreur not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<LivreurDTO> searchLivreurs(String keyword, Pageable pageable) {
        log.debug("Searching livreurs with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<LivreurDTO> getLivreursByZone(Long zoneId) {
        log.debug("Fetching livreurs by zone: {}", zoneId);
        return repository.findByZoneId(zoneId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getLivreurStatistics(Long livreurId) {
        log.debug("Fetching statistics for livreur: {}", livreurId);
        if (!repository.existsById(livreurId)) {
            throw new ResourceNotFoundException("Livreur not found with id: " + livreurId);
        }

        Long nombreColis = repository.countColisByLivreur(livreurId);
        Double poidsTotal = repository.sumPoidsByLivreur(livreurId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("livreurId", livreurId);
        stats.put("nombreColis", nombreColis);
        stats.put("poidsTotal", poidsTotal != null ? poidsTotal : 0.0);

        return stats;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getLivreurStats(Long livreurId) {
        return getLivreurStatistics(livreurId);
    }
}
