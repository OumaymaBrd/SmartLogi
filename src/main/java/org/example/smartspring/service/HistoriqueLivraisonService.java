package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HistoriqueLivraisonService {

//    private final HistoriqueLivraisonRepository repository;
//    private final HistoriqueLivraisonMapper mapper;
//
//    public List<HistoriqueColisDTO> getHistoriqueByColisId(String colisId) {
//        log.debug("Fetching historique for colis: {}", colisId);
//        return repository.findByColisIdOrderByDateChangementDesc(colisId).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<HistoriqueColisDTO> getHistoriqueByStatut(StatutColis statut) {
//        log.debug("Fetching historique by statut: {}", statut);
//        return repository.findByStatut(statut).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<HistoriqueColisDTO> getHistoriqueByPeriode(LocalDateTime dateDebut, LocalDateTime dateFin) {
//        log.debug("Fetching historique between {} and {}", dateDebut, dateFin);
//        return repository.findByDateChangementBetween(dateDebut, dateFin).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }
}
