package org.example.smartspring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientExpediteurRequestDTO {

    @NotBlank(message = "Le nom doit pas contient des espaces et null")
    @Size(max=100)
    private String nom;

    @NotBlank(message = "Votre Prenom est doit pas contient null ou des espaces")
    @Size(max=100)
    private String prenom;

    @Email
    private String email;

    @NotBlank(message = "l'adresse il est doit pas contient des contenu")
    @Size(max=50)
    private String adresse;

}
