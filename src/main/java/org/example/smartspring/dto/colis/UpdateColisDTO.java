package org.example.smartspring.dto.colis;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColisDTO {

    @DecimalMin(value = "0.1", message = "Le poids doit être supérieur à 0")
    private BigDecimal poids;

    @DecimalMin(value = "0.0", message = "La valeur déclarée doit être positive")
    private BigDecimal valeurDeclaree;

    private String description;

    private StatutColis statut;

    private PrioriteColis priorite;

    private String livreurId;

    private String zoneId;

    private String clientExpediteurId;

    private String destinataireId;
}
