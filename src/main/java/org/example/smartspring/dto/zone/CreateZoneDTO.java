package org.example.smartspring.dto.zone;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateZoneDTO {

    @NotBlank(message = "Le nom de la zone est obligatoire")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le code postal est obligatoire")
    private String codePostale;

    private String description;
}
