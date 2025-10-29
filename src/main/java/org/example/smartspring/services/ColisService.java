package org.example.smartspring.services;

import org.example.smartspring.dto.reponse.ColisResponseDTO;
import org.example.smartspring.dto.request.ColisRequestDTO;

import java.util.List;

public interface ColisService {

    ColisResponseDTO createColis(ColisRequestDTO colisRequestDTO);
    List<ColisResponseDTO> getAllColis();
    ColisResponseDTO getColisById(String id);
    void deleteColisById(String id);
}
