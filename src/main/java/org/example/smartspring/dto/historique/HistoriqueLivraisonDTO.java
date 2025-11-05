package org.example.smartspring.dto.historique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueLivraisonDTO {

    private String id;
    private Long colisId;
    private StatutColis ancienStatut;
    private StatutColis nouveauStatut;
    private LocalDateTime dateChangement;
    private String commentaire;
}
