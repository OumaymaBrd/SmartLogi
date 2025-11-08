package org.example.smartspring.service;

import jakarta.validation.constraints.DecimalMax;
import lombok.RequiredArgsConstructor;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.repository.ColisRepository;
import org.example.smartspring.repository.HistoriqueLivraisonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class HistoriqueLivraisonService {

    private final HistoriqueLivraisonRepository repository;

    @Transactional
    public HistoriqueLivraison updateDernierCommentaire(String colisId, String commentaire) {

        List<HistoriqueLivraison> historiques =
                repository.findByColisIdOrderByDateChangementDesc(colisId);

        if (historiques.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aucun historique trouvé pour ce colis : " + colisId);
        }

        HistoriqueLivraison dernier = historiques.get(0);

        // Mettre à jour le commentaire
        dernier.setCommentaire(commentaire);

        return repository.save(dernier);
    }
}
