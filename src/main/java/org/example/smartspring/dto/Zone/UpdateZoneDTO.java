package org.example.smartspring.dto.Zone;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateZoneDTO {

    @Size(max = 100)
    private String nom;

    @Size(max = 10)
    private String codePostal;
}
