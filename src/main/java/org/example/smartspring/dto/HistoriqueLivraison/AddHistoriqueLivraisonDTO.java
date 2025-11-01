package org.example.smartspring.dto.HistoriqueLivraison;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.StatutColis;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddHistoriqueLivraisonDTO {

    @NotNull(message = "Le colis est obligatoire")
    private Long colisId;

    @NotNull(message = "Le statut est obligatoire")
    private StatutColis statut;

    private String commentaire;
}
