package org.example.smartspring.dto.HistoriqueLivraison;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueLivraisonDTO {

    private Long id;

    @NotNull(message = "Le colis est obligatoire")
    private Long colisId;

    @NotNull(message = "Le statut est obligatoire")
    private StatutColis statut;

    private LocalDateTime dateChangement;

    private String commentaire;
}
