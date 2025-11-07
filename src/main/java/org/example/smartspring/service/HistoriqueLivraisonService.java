package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.historique.HistoriqueLivraisonDTO;
import org.example.smartspring.enums.StatutColis;
import org.example.smartspring.mapper.HistoriqueLivraisonMapper;
import org.example.smartspring.repository.HistoriqueLivraisonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HistoriqueLivraisonService {

//    private final HistoriqueLivraisonRepository repository;
//    private final HistoriqueLivraisonMapper mapper;
//
//    public List<HistoriqueLivraisonDTO> getHistoriqueByColisId(String colisId) {
//        log.debug("Fetching historique for colis: {}", colisId);
//        return repository.findByColisIdOrderByDateChangementDesc(colisId).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<HistoriqueLivraisonDTO> getHistoriqueByStatut(StatutColis statut) {
//        log.debug("Fetching historique by statut: {}", statut);
//        return repository.findByStatut(statut).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<HistoriqueLivraisonDTO> getHistoriqueByPeriode(LocalDateTime dateDebut, LocalDateTime dateFin) {
//        log.debug("Fetching historique between {} and {}", dateDebut, dateFin);
//        return repository.findByDateChangementBetween(dateDebut, dateFin).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
}
