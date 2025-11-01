package org.example.smartspring.dto.Produit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProduitDTO {

    @Size(max = 150)
    private String nom;

    @Size(max = 100)
    private String categorie;

    @DecimalMin(value = "0.01", message = "Le poids doit être supérieur à 0")
    private BigDecimal poids;

    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    private BigDecimal prix;
}
