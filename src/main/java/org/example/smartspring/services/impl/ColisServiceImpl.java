package org.example.smartspring.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.response.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.mapper.ColisMapper;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.services.ColisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {

    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;

    @Override
    public ColisResponseDTO createColis(ColisRequestDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        Colis saved = colisRepository.save(entity);
        return colisMapper.toResponseDTO(saved);
    }

    @Override
    public List<ColisResponseDTO> getAllColis() {
        return colisRepository.findAll()
                .stream()
                .map(colisMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ColisResponseDTO getColisById(String id) {
        return colisRepository.findById(id)
                .map(colisMapper::toResponseDTO)
                .orElse(null);
    }

    @Override
    public void deleteColisById(String id) {
        colisRepository.deleteById(id);
    }
}
