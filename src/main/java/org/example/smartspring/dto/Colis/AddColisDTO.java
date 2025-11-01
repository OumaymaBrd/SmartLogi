package org.example.smartspring.dto.Colis;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AddColisDTO {

    @Size(max = 500)
    private String description;

    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "0.01", message = "Le poids doit être supérieur à 0")
    private BigDecimal poids;

    @NotNull(message = "Le statut est obligatoire")
    private StatutColis statut;

    @NotNull(message = "La priorité est obligatoire")
    private PrioriteColis priorite;

    @NotBlank(message = "La ville de destination est obligatoire")
    @Size(max = 100)
    private String villeDestination;

    @NotNull(message = "Le client expéditeur est obligatoire")
    private Long clientExpediteurId;

    @NotNull(message = "Le destinataire est obligatoire")
    private Long destinataireId;

    private Long livreurId;

    private Long zoneId;
}
