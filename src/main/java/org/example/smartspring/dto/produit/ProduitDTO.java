package org.example.smartspring.dto.produit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {

    private String nom;
    private String description;
    private int quantite;
    private String categorie;
    private BigDecimal poids;
    private BigDecimal prix;
}
