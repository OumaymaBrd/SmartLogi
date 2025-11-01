package org.example.smartspring.dto.Colis;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.enums.PrioriteColis;
import org.example.smartspring.enums.StatutColis;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColisDTO {

    @Size(max = 500)
    private String description;

    @DecimalMin(value = "0.01", message = "Le poids doit être supérieur à 0")
    private BigDecimal poids;

    private StatutColis statut;

    private PrioriteColis priorite;

    @Size(max = 100)
    private String villeDestination;

    private Long livreurId;

    private Long zoneId;
}
