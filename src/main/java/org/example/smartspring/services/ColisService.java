package org.example.smartspring.services;

import org.example.smartspring.dto.response.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;

import java.util.List;

public interface ColisService {
    ColisResponseDTO createColis(ColisRequestDTO dto);
    List<ColisResponseDTO> getAllColis(String description);
    ColisResponseDTO getColisById(String id);
    void deleteColisById(String id);
}
