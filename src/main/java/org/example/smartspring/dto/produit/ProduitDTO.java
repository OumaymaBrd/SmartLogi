package org.example.smartspring.dto.produit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {

    private String nom;
    private String description;
    private Integer quantite;
}
