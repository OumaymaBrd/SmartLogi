package org.example.smartspring.dto.gestionnairelogistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AddGestionnaireLogistqueDTO {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
