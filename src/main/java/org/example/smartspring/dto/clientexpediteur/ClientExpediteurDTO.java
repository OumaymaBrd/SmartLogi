package org.example.smartspring.dto.clientexpediteur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientExpediteurDTO {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String ville;
}
