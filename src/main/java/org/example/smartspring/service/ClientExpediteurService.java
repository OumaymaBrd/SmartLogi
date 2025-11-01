package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.ClientExpediteur.AddClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.ClientExpediteurDTO;
import org.example.smartspring.dto.ClientExpediteur.UpdateClientExpediteurDTO;
import org.example.smartspring.entities.ClientExpediteur;
import org.example.smartspring.mapper.ClientExpediteurMapper;
import org.example.smartspring.repository.ClientExpediteurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientExpediteurService {

    private final ClientExpediteurRepository repository;
    private final ClientExpediteurMapper mapper;

    @Transactional
    public ClientExpediteurDTO create(AddClientExpediteurDTO dto) {
        log.info("Creating new client expediteur with email: {}", dto.getEmail());

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà");
        }

        ClientExpediteur entity = mapper.toEntity(dto);
        ClientExpediteur saved = repository.save(entity);

        log.info("Client expediteur created with ID: {}", saved.getId());
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public ClientExpediteurDTO getById(Long id) {
        log.debug("Fetching client expediteur with ID: {}", id);
        ClientExpediteur entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client expéditeur non trouvé avec l'ID: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<ClientExpediteurDTO> getAll(Pageable pageable) {
        log.debug("Fetching all clients expediteurs with pagination");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ClientExpediteurDTO> search(String keyword, Pageable pageable) {
        log.debug("Searching clients expediteurs with keyword: {}", keyword);
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDto);
    }

    @Transactional
    public ClientExpediteurDTO update(Long id, UpdateClientExpediteurDTO dto) {
        log.info("Updating client expediteur with ID: {}", id);

        ClientExpediteur entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client expéditeur non trouvé avec l'ID: " + id));

        if (dto.getEmail() != null && !dto.getEmail().equals(entity.getEmail())) {
            if (repository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException("Un client avec cet email existe déjà");
            }
        }

        mapper.updateEntityFromDto(dto, entity);
        ClientExpediteur updated = repository.save(entity);

        log.info("Client expediteur updated with ID: {}", updated.getId());
        return mapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting client expediteur with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Client expéditeur non trouvé avec l'ID: " + id);
        }

        repository.deleteById(id);
        log.info("Client expediteur deleted with ID: {}", id);
    }
}
