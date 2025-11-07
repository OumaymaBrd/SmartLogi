package org.example.smartspring.dto.colis;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddColisDTO {

    @NotBlank(message = "Le numéro de suivi est obligatoire")
    private String numeroSuivi;

    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "0.1", message = "Le poids doit être supérieur à 0")
    private BigDecimal poids;

    @NotNull(message = "La valeur déclarée est obligatoire")
    @DecimalMin(value = "0.0", message = "La valeur déclarée doit être positive")
    private BigDecimal valeurDeclaree;

    private String description;

    @NotNull(message = "La priorité est obligatoire")
    private PrioriteColis priorite;

    @NotNull(message = "L'ID du client expéditeur est obligatoire")
    private String clientExpediteurId;

    @NotNull(message = "L'ID du destinataire est obligatoire")
    private String destinataireId;

    private String livreurId;

    @NotNull(message = "L'ID de la zone est obligatoire")
    private String zoneId;

    private String livreur_id_livree;
}
