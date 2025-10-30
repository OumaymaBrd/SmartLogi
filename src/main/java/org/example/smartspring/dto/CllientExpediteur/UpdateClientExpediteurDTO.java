package org.example.smartspring.dto.CllientExpediteur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class UpdateClientExpediteurDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
}
