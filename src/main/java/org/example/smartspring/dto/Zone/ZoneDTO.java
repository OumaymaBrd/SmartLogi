package org.example.smartspring.dto.Zone;

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
public class ZoneDTO {

    private Long id;

    @NotBlank(message = "Le nom de la zone est obligatoire")
    @Size(max = 100)
    private String nom;

    @NotBlank(message = "Le code postal est obligatoire")
    @Size(max = 10)
    private String codePostal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
