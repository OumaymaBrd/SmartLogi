package org.example.smartspring.dto.gestionnaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GestionnaireLogistiqueDTO {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
