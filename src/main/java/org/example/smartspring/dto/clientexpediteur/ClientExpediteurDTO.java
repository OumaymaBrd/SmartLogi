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

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
}
