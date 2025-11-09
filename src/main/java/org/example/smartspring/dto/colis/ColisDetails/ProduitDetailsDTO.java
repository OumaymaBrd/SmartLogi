package org.example.smartspring.dto.colis.ColisDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProduitDetailsDTO {
    private String nom;
    private BigDecimal prixUnitaire;
}
