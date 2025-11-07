package org.example.smartspring.dto.gestionnairelogistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class GestionnaireLogistiqueDTO {

    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;


}
