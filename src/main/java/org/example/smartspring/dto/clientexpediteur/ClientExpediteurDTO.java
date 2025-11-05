package org.example.smartspring.dto.clientexpediteur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class ClientExpediteurDTO {

    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;


}
