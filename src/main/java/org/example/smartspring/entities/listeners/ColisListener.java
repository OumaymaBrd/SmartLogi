package org.example.smartspring.entities.listeners;

import jakarta.persistence.PreUpdate;
import org.example.smartspring.entities.Colis;
import org.example.smartspring.entities.HistoriqueLivraison;
import org.example.smartspring.repository.HistoriqueLivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ColisListener {

    private static HistoriqueLivraisonRepository historiqueRepo;

    @Autowired
    public void setHistoriqueRepo(HistoriqueLivraisonRepository repo) {
        ColisListener.historiqueRepo = repo;
    }

    @PreUpdate
    public void preUpdate(Colis colis) {

        // On vérifie si le statut a changé
        if (colis.getStatut() != null) {

            HistoriqueLivraison historique = HistoriqueLivraison.builder()
                    .colisId(colis.getId())
                    .livreurId(
                            colis.getLivreur() != null ? colis.getLivreur().getId() :
                                    colis.getLivreurLivree() != null ? colis.getLivreurLivree().getId() :
                                            null
                    )
                    .statut(colis.getStatut())
                    .dateChangement(LocalDateTime.now())
                    .commentaire(null)
                    .build();

            historiqueRepo.save(historique);
        }
    }
}
