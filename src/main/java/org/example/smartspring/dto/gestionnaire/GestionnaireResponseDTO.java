package org.example.smartspring.dto.gestionnaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GestionnaireResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDateTime dateCreation;
    private Boolean actif;
}
