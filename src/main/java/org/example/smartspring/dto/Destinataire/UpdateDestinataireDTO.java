package org.example.smartspring.dto.Destinataire;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDestinataireDTO {

    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String prenom;

    @Email(message = "L'email doit être valide")
    @Size(max = 150)
    private String email;

    @Size(max = 20)
    private String telephone;

    @Size(max = 255)
    private String adresse;
}
