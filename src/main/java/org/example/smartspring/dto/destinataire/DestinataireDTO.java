package org.example.smartspring.dto.destinataire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinataireDTO {

    private String id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
}
