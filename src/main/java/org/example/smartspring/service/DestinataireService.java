package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.destinataire.AddDestinataireDTO;
import org.example.smartspring.dto.destinataire.DestinataireDTO;
import org.example.smartspring.dto.destinataire.UpdateDestinataireDTO;
import org.example.smartspring.entities.Destinataire;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.DestinataireMapper;
import org.example.smartspring.repository.DestinataireRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DestinataireService {

    private final DestinataireRepository repository;
    private final DestinataireMapper mapper;

    public DestinataireDTO createDestinataire(AddDestinataireDTO dto) {
        log.debug("Creating new destinataire: {} {}", dto.getNom(), dto.getPrenom());
        Destinataire entity = mapper.toEntity(dto);
        Destinataire saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<DestinataireDTO> getAllDestinataires(Pageable pageable) {
        log.debug("Fetching all destinataires with pagination");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public DestinataireDTO getDestinataireById(String id) {
        log.debug("Fetching destinataire by id: {}", id);
        Destinataire entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destinataire not found with id: " + id));
        return mapper.toDto(entity);
    }

    public DestinataireDTO updateDestinataire(String id, UpdateDestinataireDTO dto) {
        log.debug("Updating destinataire with id: {}", id);
        Destinataire entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destinataire not found with id: " + id));

        mapper.updateEntityFromDto(dto, entity);
        Destinataire updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    public void deleteDestinataire(String id) {
        log.debug("Deleting destinataire with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Destinataire not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<DestinataireDTO> searchDestinataires(String keyword, Pageable pageable) {
        log.debug("Searching destinataires with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DestinataireDTO> getDestinatairesByVille(String ville) {
        log.debug("Fetching destinataires by ville: {}", ville);
        return repository.findByVille(ville).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
