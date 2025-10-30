package org.example.smartspring.dto.CllientExpediteur;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.smartspring.entities.ClientExpediteur;
import org.mapstruct.Mapper;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ClientExpediteurDTO {

    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    @NotBlank(message = "Remplir Le Numero Teliphone")
    private String telephone;



}
