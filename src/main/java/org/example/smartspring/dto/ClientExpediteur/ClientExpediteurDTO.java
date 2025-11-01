package org.example.smartspring.dto.ClientExpediteur;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ClientExpediteurDTO {

    private String nom;
    private String prenom;
    private String email;

    private String adresse;
    @NotBlank(message = "Remplir Le Numero Teliphone")

    private String telephone;




}
