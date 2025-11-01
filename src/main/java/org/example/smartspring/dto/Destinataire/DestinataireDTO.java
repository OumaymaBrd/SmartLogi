package org.example.smartspring.dto.Destinataire;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinataireDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100)
    private String prenom;

    @Email(message = "L'email doit être valide")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Size(max = 20)
    private String telephone;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255)
    private String adresse;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
