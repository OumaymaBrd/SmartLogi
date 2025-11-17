package org.example.smartspring.service.training;

import lombok.RequiredArgsConstructor;
import org.example.smartspring.dto.training.TrainingDTO;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.mapper.traing.ClientExpediteurHelper;
import org.example.smartspring.mapper.traing.TraingMapper;
import org.example.smartspring.repository.HistoriqueLivraisonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineService {

    private final HistoriqueLivraisonRepository historiqueRepo;
    private final TraingMapper mapper;
    private final ClientExpediteurHelper helper;

    public List<TrainingDTO> getAll() {
        return historiqueRepo.findAll()
                .stream()
                .map(h -> mapper.mapToDTO(h, helper))  // ✅ helper bien passé ici
                .toList();
    }
}
