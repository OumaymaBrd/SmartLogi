package org.example.smartspring.dto.colis;

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
public class HistoriqueColisDTO {
    private String id;
    private String colisId;
    private String gestionnaireId;
    private String gestionnaireNom;
    private String gestionnairePrenom;
    private String action;
    private StatutColis statut;
    private String commentaire;
    private LocalDateTime dateChangement;
}
