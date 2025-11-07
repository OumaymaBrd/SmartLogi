package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.entities.Zone;
import org.example.smartspring.mapper.ZoneMapper;
import org.example.smartspring.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;

    @Transactional
    public ZoneDTO creerZone(ZoneDTO dto) {
        Zone zone = zoneMapper.toEntity(dto);
        Zone saved = zoneRepository.save(zone);
        return zoneMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public ZoneDTO obtenirZone(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone non trouvée avec l'ID: " + id));
        return zoneMapper.toDTO(zone);
    }

    @Transactional(readOnly = true)
    public Optional<ZoneDTO> obtenirZoneParId(String id) {
        return zoneRepository.findById(id)
                .map(zoneMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ZoneDTO> obtenirZoneParNom(String nom) {
        return zoneRepository.findByNom(nom)
                .map(zoneMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ZoneDTO> obtenirToutesLesZones() {
        return zoneMapper.toDTOList(zoneRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ZoneDTO> rechercherZones(String keyword) {
        return zoneMapper.toDTOList(zoneRepository.rechercherZones(keyword));
    }

    @Transactional
    public ZoneDTO modifierZone(String id, ZoneDTO dto) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone non trouvée avec l'ID: " + id));

        zone.setNom(dto.getNom());
        zone.setDescription(dto.getDescription());
        zone.setCodePostal(dto.getCodePostal());

        Zone updated = zoneRepository.save(zone);
        return zoneMapper.toDTO(updated);
    }

    @Transactional
    public void supprimerZone(String id) {
        if (!zoneRepository.existsById(id)) {
            throw new RuntimeException("Zone non trouvée avec l'ID: " + id);
        }
        zoneRepository.deleteById(id);
    }
}
